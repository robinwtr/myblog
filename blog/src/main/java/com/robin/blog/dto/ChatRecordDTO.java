package com.robin.blog.dto;

import com.robin.blog.domain.ChatRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 11:13
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRecordDTO {

    /**
     * 聊天记录
     */
    private List<ChatRecord> chatRecordList;

    /**
     * ip地址
     */
    private String ipAddr;

    /**
     * ip来源
     */
    private String ipSource;
}
