package com.robin.blog.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 10:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "角色")
public class RoleVO {

    @ApiModelProperty(name = "id", value = "用户id", dataType = "Integer")
    private Integer id;

    @NotBlank(message = "角色名不能为空")
    @ApiModelProperty(name = "categoryName", value = "标签名", required = true, dataType = "String")
    private String roleName;

    @NotBlank(message = "权限标签不能为空")
    @ApiModelProperty(name = "categoryName", value = "标签名", required = true, dataType = "String")
    private String roleLabel;

    @ApiModelProperty(name = "resourceIdList", value = "资源列表", required = true, dataType = "List<Integer>")
    private List<Integer> resourceIdList;

    @ApiModelProperty(name = "menuIdList", value = "菜单列表", required = true, dataType = "List<Integer>")
    private List<Integer> menuIdList;
}
