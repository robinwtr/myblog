package com.robin.blog.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * @author robin
 * @version 1.0
 * @date 2021/12/11 16:04
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_operation_log")
public class OperationLog {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 操作模块
     */
    private String optModule;

    /**
     * 操作路径
     */
    private String optUrl;

    /**
     * 操作类型
     */
    private String optType;

    /**
     * 操作方法
     */
    private String optMethod;

    /**
     * 操作描述
     */
    private String optDesc;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 返回数据
     */
    private String responseData;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户登录ip
     */
    private String ipAddr;

    /**
     * ip 来源
     */
    private String ipSource;

    /**
     * 创建时间
     */
    private Date createTime;
}