package com.robin.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robin.blog.constant.CommonConst;
import com.robin.blog.dao.CommentDao;
import com.robin.blog.dao.UserInfoDao;
import com.robin.blog.domain.Comment;
import com.robin.blog.dto.*;
import com.robin.blog.service.CommentService;
import com.robin.blog.utils.HTMLUtil;
import com.robin.blog.utils.UserUtil;
import com.robin.blog.vo.CommentVO;
import com.robin.blog.vo.ConditionVO;
import com.robin.blog.vo.DeleteVO;
import org.apache.catalina.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.robin.blog.constant.CommonConst.*;
import static com.robin.blog.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.robin.blog.constant.RedisPrefixConst.COMMENT_LIKE_COUNT;
import static com.robin.blog.constant.RedisPrefixConst.COMMENT_USER_LIKE;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 15:33
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public PageDTO<CommentDTO> listComments(Integer articleId, Long current) {
        //查询文章评论量
        Integer commentCount = commentDao.selectCount(new LambdaQueryWrapper<Comment>()
                .eq(Objects.nonNull(articleId), Comment::getArticleId, articleId)
                .isNull(Objects.isNull(articleId), Comment::getArticleId)
                .isNull(Comment::getParentId)
                .eq(Comment::getIsDelete, CommonConst.FALSE));
        if (commentCount == 0) return new PageDTO<>();
        //分页查询评论集合r
        List<CommentDTO> commentDTOList = commentDao.listComments(articleId, (current - 1) * 10);
        //查询redis的评论点赞数据
        Map<String, Integer> likeCountMap = (Map<String, Integer>) redisTemplate.boundHashOps(COMMENT_LIKE_COUNT).entries();
        //提取评论id集合
        List<Integer> commentIdList = new ArrayList<>();
        //封装评论点赞量
        commentDTOList.forEach(item ->{
            commentIdList.add(item.getId());
            item.setLikeCount(Objects.requireNonNull(likeCountMap).get(item.getId().toString()));
        });
        //根据评论id集合查询恢复数据
        List<ReplyDTO> replyDTOList = commentDao.listReplies(commentIdList);
        //封装回复数量
        replyDTOList.forEach(item -> item.setLikeCount(Objects.requireNonNull(likeCountMap).get(item.getId().toString())));
        //根据评论id分组回复数据
        Map<Integer, List<ReplyDTO>> replyMap = replyDTOList.stream().collect(Collectors.groupingBy(ReplyDTO::getReplyId));
        //根据评论id查询回复量
        Map<Integer, Integer> replyCountMap = commentDao.listReplyCountByCommentId(commentIdList)
                .stream().collect(Collectors.toMap(ReplyCountDTO::getCommentId,ReplyCountDTO::getReplyCount));
        //对评论进行封装
        commentDTOList.forEach(item -> {
            item.setReplyDTOList(replyMap.get(item.getId()));
            item.setReplyCount(replyCountMap.get(item.getId()));
        });
        return new PageDTO<>(commentDTOList, commentCount);
    }

    @Override
    public List<ReplyDTO> listRepliesByCommentId(Integer commentId, Long current) {
        List<ReplyDTO> replyDTOList = commentDao.listRepliesByCommentId(commentId, (current - 1) * 5);
        Map<String, Integer> likeCountMap = (Map<String, Integer>) redisTemplate.boundHashOps(COMMENT_LIKE_COUNT).entries();
        replyDTOList.forEach(item -> item.setLikeCount(Objects.requireNonNull(likeCountMap).get(item.getId().toString())));
        return replyDTOList;
    }

    @Override
    public void saveComment(CommentVO commentVO) {
        // 过滤html标签
        commentVO.setCommentContent(HTMLUtil.deleteCommentTag(commentVO.getCommentContent()));
        Comment comment = Comment.builder()
                .userId(UserUtil.getLoginUser().getUserInfoId())
                .replyId(commentVO.getReplyId())
                .articleId(commentVO.getArticleId())
                .commentContent(commentVO.getCommentContent())
                .parentId(commentVO.getParentId())
                .createTime(new Date())
                .build();
        commentDao.insert(comment);
        // 通知用户
        notice(commentVO);
    }

    /**
     * 通知评论用户
     * @param commentVO 评论信息
     */
    private void notice(CommentVO commentVO) {
        // 判断是回复用户还是评论作者
        Integer userId = Objects.nonNull(commentVO.getReplyId()) ? commentVO.getReplyId() : BLOGGER_ID;
        // 查询邮箱号
        String email = userInfoDao.selectById(userId).getEmail();
        if (StringUtils.isNotBlank(email)) {
            // 判断页面路径
            String url = Objects.nonNull(commentVO.getArticleId()) ? URL + ARTICLE_PATH + commentVO.getArticleId() : URL + LINK_PATH;
            // 发送消息
            EmailDTO emailDTO = EmailDTO.builder()
                    .email(email)
                    .subject("评论提醒")
                    .content("您收到了一条新的回复，请前往" + url + "\n页面查看")
                    .build();
            rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCommentLike(Integer commentId) {
        // 查询当前用户点赞过的评论id集合
        HashSet<Integer> commentLikeSet = (HashSet<Integer>) redisTemplate.boundHashOps(COMMENT_USER_LIKE).get(UserUtil.getLoginUser().getUserInfoId().toString());
        // 第一次点赞则创建
        if (CollectionUtils.isEmpty(commentLikeSet)) {
            commentLikeSet = new HashSet<>();
        }
        // 判断是否点赞
        if (commentLikeSet.contains(commentId)) {
            // 点过赞则删除评论id
            commentLikeSet.remove(commentId);
            // 评论点赞量-1
            redisTemplate.boundHashOps(COMMENT_LIKE_COUNT).increment(commentId.toString(), -1);
        } else {
            // 未点赞则增加评论id
            commentLikeSet.add(commentId);
            // 评论点赞量+1
            redisTemplate.boundHashOps(COMMENT_LIKE_COUNT).increment(commentId.toString(), 1);
        }
        // 保存点赞记录
        redisTemplate.boundHashOps(COMMENT_USER_LIKE).put(UserUtil.getLoginUser().getUserInfoId().toString(), commentLikeSet);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCommentDelete(DeleteVO deleteVO) {
        // 修改评论逻辑删除状态
        List<Comment> commentList = deleteVO.getIdList().stream()
                .map(id -> Comment.builder().id(id).isDelete(deleteVO.getIsDelete()).build())
                .collect(Collectors.toList());
        this.updateBatchById(commentList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PageDTO<CommentBackDTO> listCommentBackDTO(ConditionVO condition) {
        // 转换页码
        condition.setCurrent((condition.getCurrent() - 1) * condition.getSize());
        // 统计后台评论量
        Integer count = commentDao.countCommentDTO(condition);
        if (count == 0) return new PageDTO<>();
        // 查询后台评论集合
        List<CommentBackDTO> commentBackDTOList = commentDao.listCommentBackDTO(condition);
        // 获取评论点赞量
        Map<String, Integer> likeCountMap = redisTemplate.boundHashOps(COMMENT_LIKE_COUNT).entries();
        //封装点赞量
        commentBackDTOList.forEach(item -> item.setLikeCount(Objects.requireNonNull(likeCountMap).get(item.getId().toString())));
        return new PageDTO<>(commentBackDTOList, count);
    }
}
