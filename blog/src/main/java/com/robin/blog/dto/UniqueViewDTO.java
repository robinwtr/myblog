package com.robin.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 11:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniqueViewDTO {

    /**
     * 日期
     */
    private String day;

    /**
     * 访问量
     */
    private Integer viewsCount;
}
