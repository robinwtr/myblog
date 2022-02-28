package com.robin.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robin.blog.dao.ChatRecordDao;
import com.robin.blog.domain.ChatRecord;
import com.robin.blog.service.ChatRecordService;
import com.robin.blog.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 15:18
 */
@Service
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordDao, ChatRecord> implements ChatRecordService {
    @Autowired
    private ChatRecordDao chatRecordDao;

    @Override
    public void deleteChartRecord() {
        String  time = DateUtil.getMinTime(DateUtil.getSomeDay(new Date(), -7));
        chatRecordDao.delete(new LambdaQueryWrapper<ChatRecord>()
                .le(ChatRecord::getCreateTime, time));
    }
}
