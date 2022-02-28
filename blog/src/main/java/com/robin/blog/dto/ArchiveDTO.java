package com.robin.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 10:00
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 发表时间
     */
    private Date createTime;
}
