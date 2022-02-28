package com.robin.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 13:39
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDTO {

    /**
     * 邮箱号
     */
    private String email;

    /**
     * 主题
     */
    private String subject;

    /**
     * 内容
     */
    private String content;
}
