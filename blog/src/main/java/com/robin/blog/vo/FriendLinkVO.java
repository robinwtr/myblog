package com.robin.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 9:53
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "友链")
public class FriendLinkVO {

    @ApiModelProperty(name = "id", value = "友链id", dataType = "Integer")
    private Integer id;

    @NotBlank(message = "链接名不能为空")
    @ApiModelProperty(name = "linkName", value = "友链名称", dataType = "String")
    private String linkName;

    @NotBlank(message = "链接头像不能为空")
    @ApiModelProperty(name = "linkAvatar", value = "友链头像", dataType = "String")
    private String linkAvatar;

    @NotBlank(message = "链接地址不能为空")
    @ApiModelProperty(name = "linkAddress", value = "链接地址", dataType = "String")
    private String linkAddress;

    @NotBlank(message = "链接介绍不能为空")
    @ApiModelProperty(name = "linkIntro", value = "链接介绍", dataType = "String")
    private String linkIntro;

}
