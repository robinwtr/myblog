package com.robin.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 10:12
 */

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TagDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 标签名
     */
    private String tagName;
}
