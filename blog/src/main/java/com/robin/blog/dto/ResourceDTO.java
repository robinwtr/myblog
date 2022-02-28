package com.robin.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.client.license.LicensesStatus;

import java.util.Date;
import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 14:12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourceDTO {

    /**
     * 权限id
     */
    private Integer id;

    /**
     * 资源名
     */
    private String resourceName;

    /**
     * 权限路径
     */
    private String url;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否禁用
     */
    private Integer isDisable;

    /**
     * 是否匿名访问
     */
    private Integer isAnonymous;

    /**
     * 权限列表
     */
    private List<ResourceDTO> children;
}