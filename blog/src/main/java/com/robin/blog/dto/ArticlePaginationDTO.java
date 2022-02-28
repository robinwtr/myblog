package com.robin.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.SignStyle;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 10:21
 */

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ArticlePaginationDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 标题
     */
    private String articleTitle;

}
