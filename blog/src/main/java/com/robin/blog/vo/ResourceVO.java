package com.robin.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 10:21
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "资源")
public class ResourceVO {

    @ApiModelProperty(name = "id", value = "资源id", required = true, dataType = "Integer")
    private Integer id;

    @NotBlank(message = "资源名不能为空")
    @ApiModelProperty(name = "resourceName", value = "资源名", required = true, dataType = "String")
    private String resourceName;

    @ApiModelProperty(name = "url", value = "资源路径", required = true, dataType = "String")
    private String url;

    @ApiModelProperty(name = "url", value = "资源路径", required = true, dataType = "String")
    private String requestMethod;

    @ApiModelProperty(name = "parentId", value = "父资源id", required = true, dataType = "Integer")
    private Integer parentId;

    @ApiModelProperty(name = "isDisable", value = "是否禁用", required = true, dataType = "Integer")
    private Integer isDisable;

    @ApiModelProperty(name = "isAnonymous", value = "是否匿名访问", required = true, dataType = "Integer")
    private Integer isAnonymous;
}
