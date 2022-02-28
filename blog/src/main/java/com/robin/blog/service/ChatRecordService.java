package com.robin.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.robin.blog.domain.ChatRecord;


/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 9:34
 */
public interface ChatRecordService extends IService<ChatRecord> {

    /**
     * 删除7天前的聊天记录
     */
    void deleteChartRecord();
}
