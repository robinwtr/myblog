package com.robin.blog.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 15:56
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "查询条件")
public class ConditionVO {

    /**
     * 分类id
     */
    @ApiModelProperty(name = "categoryId", value = "分类id", dataType = "Integer")
    private Integer categoryId;

    /**
     * 标签id
     */
    @ApiModelProperty(name = "tagId", value = "标签id", dataType = "Integer")
    private Integer tagId;

    /**
     * 当前页码
     */
    @ApiModelProperty(name = "current", value = "当前页码", dataType = "Integer")
    private Integer current;

    /**
     * 显示数量
     */
    @ApiModelProperty(name = "size", value = "显示数量", dataType = "Integer")
    private Integer size;

    /**
     * 搜索内容
     */
    @ApiModelProperty(name = "keywords", value = "搜索内容", required = true, dataType = "String")
    private String keywords;

    /**
     * 状态值
     */
    @ApiModelProperty(name = "isDelete", value = "是否删除", dataType = "Integer")
    private Integer isDelete;

    /**
     * 是否为草稿
     */
    @ApiModelProperty(name = "isDraft", value = "是否为草稿", dataType = "Integer")
    private Integer isDraft;

    /**
     * 开始时间
     */
    @ApiModelProperty(name = "startTime", value = "开始时间", dataType = "Date")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(name = "endTime", value = "结束时间", dataType = "Date")
    private Date endTime;
}
