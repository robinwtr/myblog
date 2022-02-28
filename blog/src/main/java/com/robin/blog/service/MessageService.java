package com.robin.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.robin.blog.domain.Message;
import com.robin.blog.dto.MessageBackDTO;
import com.robin.blog.dto.MessageDTO;
import com.robin.blog.dto.PageDTO;
import com.robin.blog.vo.ConditionVO;
import com.robin.blog.vo.MessageVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 10:07
 */
@Repository
public interface MessageService extends IService<Message> {

    /**
     * 添加留言弹幕
     * @param messageVO 留言对象
     */
    void saveMessage(MessageVO messageVO);

    /**
     * 查看留言弹幕
     * @return 留言列表
     */
    List<MessageDTO> listMessages();

    /**
     * 查看后台留言
     * @param conditionVO 条件
     * @return 留言列表
     */
    PageDTO<MessageBackDTO> listMessageBackDTO(ConditionVO conditionVO);
}
