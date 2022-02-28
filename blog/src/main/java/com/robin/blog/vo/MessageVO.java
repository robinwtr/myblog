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
 * @date 2021/12/17 10:08
 */

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ApiModel(description = "留言")
public class MessageVO {

    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty(name = "nickName", value = "昵称", required = true, dataType = "String")
    private String nickName;

    @NotBlank(message = "头像不能为空")
    @ApiModelProperty(name = "avatar", value = "头像", required = true, dataType = "String")
    private String avatar;

    @NotBlank(message = "留言内容不能为空")
    @ApiModelProperty(name = "messageContent", value = "留言内容",required = true,dataType = "String")
    private String messageContent;

    @NotBlank(message = "弹幕速度不能为空")
    @ApiModelProperty(name = "time", value = "弹幕速度", required = true, dataType = "Integer")
    private Integer time;
}
