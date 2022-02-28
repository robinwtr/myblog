package com.robin.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 10:37
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryBackDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 分类名
     */
    private String categoryName;
}
