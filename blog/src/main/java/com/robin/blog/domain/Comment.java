package com.robin.blog.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/11 15:38
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_comment")
public class Comment {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评论用户id
     */
     private Integer userId;

    /**
     * 回复用户id
     */
    private Integer replyId;

    /**
     * 评论文章id
     */
    private Integer articleId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论时间
     */
    private Date createTime;

    /**
     * 父评论id
     */
    private Integer parentId;

    /**
     * 状态码
     */
    private Integer isDelete;
}
