package com.robin.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 14:53
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebSocketMessageDTO {

    /**
     * 类型
     */
    private Integer type;

    /**
     * 数据
     */
    private Object data;
}
