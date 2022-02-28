package com.robin.blog.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/14 11:06
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "逻辑删除")
public class DeleteVO {
    /**
     * id列表
     */
    @ApiModelProperty(name = "idList", value = "id列表", required = true, dataType = "List<Integer>")
    private List<Integer> idList;

    /**
     * 状态值
     */
    @ApiModelProperty(name = "isDelete", value = "删除状态", required = true, dataType = "Integer")
    private Integer isDelete;
}
