package com.robin.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 13:41
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendLinkBackDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 链接名
     */
    private String linkName;

    /**
     * 链接头像
     */
    private String linkAvatar;

    /**
     * 链接介绍
     */
    private String linkIntro;

    /**
     * 链接地址
     */
    private String linkAddress;

    /**
     * 创建时间
     */
    private Date createTime;
}
