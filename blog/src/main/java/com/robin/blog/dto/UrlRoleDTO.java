package com.robin.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 14:21
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlRoleDTO {

    /**
     * 资源id
     */
    private Integer id;

    /**
     * 路径
     */
    private String url;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 角色名
     */
    private List<String> roleList;

    /**
     * 是否匿名
     */
    private Integer isAnonymous;

}
