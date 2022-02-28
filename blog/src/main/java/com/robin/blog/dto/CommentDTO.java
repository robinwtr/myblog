package com.robin.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.lucene.util.fst.UpToTwoPositiveIntOutputs;

import java.util.Date;
import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 11:20
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    /**
     * 评论id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 个人网站
     */
    private String webSite;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论时间
     */
    private Date createTime;

    /**
     * 回复量
     */
    private Integer replyCount;

    /**
     * 回复列表
     */
    private List<ReplyDTO> replyDTOList;
}
