package com.robin.blog.dto;

import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 11:15
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentBackDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 被回复用户昵称
     */
    private String replyNickname;

    /**
     * 文章标题
     */
    private String articleName;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 发白时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private Integer isDelete;

}
