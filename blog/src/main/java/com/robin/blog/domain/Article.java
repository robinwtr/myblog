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
 * @date 2021/12/11 14:55
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_article")
public class Article {
    /**
     * 文章 id 识别特定文章
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     *  作者 userid 写文章作者
     */
    private Integer userId;

    /**
     * 文章分类
     */
    private Integer categoryId;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 内容
     */
    private String articleContent;

    /**
     * 发表时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否置顶
     */
    private Integer isTop;

    /**
     * 是否是草稿
     */
    private Integer isDraft;

    /**
     * 状态码
     */
    private Integer isDelete;
}