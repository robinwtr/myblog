package com.robin.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mustachejava.codes.CommentCode;
import com.robin.blog.domain.Comment;
import com.robin.blog.dto.CommentBackDTO;
import com.robin.blog.dto.CommentDTO;
import com.robin.blog.dto.PageDTO;
import com.robin.blog.dto.ReplyDTO;
import com.robin.blog.vo.CommentVO;
import com.robin.blog.vo.ConditionVO;
import com.robin.blog.vo.DeleteVO;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 9:35
 */
public interface CommentService extends IService<Comment> {

    /**
     * 查看评论
     * @param articleId 文章id
     * @param current 页码
     * @return 评论集合
     */
    PageDTO<CommentDTO> listComments(Integer articleId, Long current);

    /**
     * 添加评论
     * @param commentId 评论id
     * @param current 页码
     * @return 回复集合
     */
    List<ReplyDTO> listRepliesByCommentId(Integer commentId, Long current);

    /**
     * 添加评论
     * @param commentVO
     */
    void saveComment(CommentVO commentVO);

    /**
     * 点赞评论
     * @param commentId 评论id
     */
    void saveCommentLike(Integer commentId);

    /**
     * 恢复或删除评论
     * @param deleteVO 逻辑删除对象
     */
    void updateCommentDelete(DeleteVO deleteVO);

    /**
     * 查询后台评论
     * @param conditionVO 条件
     * @return 评论列表
     */
    PageDTO<CommentBackDTO> listCommentBackDTO(ConditionVO conditionVO);
}
