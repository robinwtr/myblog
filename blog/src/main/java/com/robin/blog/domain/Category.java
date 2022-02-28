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
 * @author robin
 * @version 1.0
 * @date 2021/12/11 15:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_category")
public class Category {
    /**
     *  id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     *  分类名
     */
    private String categoryName;

    /**
     * 创建时间
     */
    private Date createTime;
}
