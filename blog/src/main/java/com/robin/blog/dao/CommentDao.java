package com.robin.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robin.blog.domain.Comment;
import com.robin.blog.dto.CommentBackDTO;
import com.robin.blog.dto.CommentDTO;
import com.robin.blog.dto.ReplyCountDTO;
import com.robin.blog.dto.ReplyDTO;
import com.robin.blog.vo.ConditionVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/14 9:43
 */

@Repository
public interface CommentDao extends BaseMapper<Comment> {

    /**
     * 查看评论
     * @param articleId 文章Id
     * @param current 当前页码
     * @return  评论集合
     */
    List<CommentDTO> listComments(@Param("articleId") Integer articleId, @Param("current") Long current);

    /**
     * 查看评论id集合下的回复
     * @param commentIdList 评论id集合
     * @return 回复集合
     */
    List<ReplyDTO> listReplies(@Param("commentIdList") List<Integer> commentIdList);

    /**
     *  查看当条评论下的回复
     * @param commentId 评论id
     * @param current 当前页码
     * @return 回复集合
     */
    List<ReplyDTO> listRepliesByCommentId(@Param("commentId") Integer commentId, @Param("current") Long current);

    /**
     * 根据评论id查询回复总量
     * @param commentIdList 评论id集合
     * @return 回复数量
     */
    List<ReplyCountDTO> listReplyCountByCommentId(@Param("commentIdList") List<Integer> commentIdList);

    /**
     * 查询后台评论
     * @param condition 条件
     * @return 评论集合
     */
    List<CommentBackDTO> listCommentBackDTO(@Param("condition")ConditionVO condition);

    /**
     * 统计后台评论数量
     * @param condition 条件
     * @return 评论数量
     */
    Integer countCommentDTO(@Param("condition") ConditionVO condition);
}
