package com.robin.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 14:52
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOnlineDTO {

    /**
     * 用户信息id
     */
    private Integer userInfoId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户登录ip
     */
    private String ipAddr;

    /**
     * ip来源
     */
    private String ipSource;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 最近登录时间
     */
    private Date lastLoginTime;
}
