package com.robin.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 10:36
 */

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ArticleOptionDTO {

    /**
     * 文章标签
     */
    private List<TagDTO> tagDTOList;

    /**
     * 文章分类列表
     */
    private List<CategoryBackDTO> categoryBackDTOList;
}
