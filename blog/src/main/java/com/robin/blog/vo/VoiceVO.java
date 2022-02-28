package com.robin.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 音频VO
 * @author: yezhiqiu
 * @date: 2021-02-21
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "音频")
public class VoiceVO {

    @ApiModelProperty(name = "type", value = "消息类型", required = true, dataType = "Integer")
    private Integer type;

    @ApiModelProperty(name = "file", value = "文件", required = true, dataType = "MultipartFile")
    private MultipartFile file;

    @ApiModelProperty(name = "userId", value = "用户id", required = true, dataType = "Integer")
    private Integer userId;

    @ApiModelProperty(name = "nickname", value = "用户昵称", required = true, dataType = "String")
    private String nickname;

    @ApiModelProperty(name = "avatar", value = "用户头像", required = true, dataType = "String")
    private String avatar;

    @ApiModelProperty(name = "content", value = "聊天内容", required = true, dataType = "String")
    private String content;

    @ApiModelProperty(name = "createTime", value = "创建时间", required = true, dataType = "Date")
    private Date createTime;

    @ApiModelProperty(name = "ipAddr", value = "用户登录ip", required = true, dataType = "String")
    private String ipAddr;

    @ApiModelProperty(name = "ipSource", value = "ip来源", required = true, dataType = "String")
    private String ipSource;

}
