package com.robin.blog.controller;


import com.robin.blog.annotation.OptLog;
import com.robin.blog.constant.StatusConst;
import com.robin.blog.dto.MessageBackDTO;
import com.robin.blog.dto.PageDTO;
import com.robin.blog.vo.*;
import com.robin.blog.dto.MessageDTO;
import com.robin.blog.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.robin.blog.constant.OptTypeConst.REMOVE;

/**
 * @author robin
 * @since 2021-12-23
 */
@Api(tags = "留言模块")
@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "添加留言")
    @PostMapping("/messages")
    public Result saveMessage(@Valid @RequestBody MessageVO messageVO) {
        messageService.saveMessage(messageVO);
        return new Result<>(true, StatusConst.OK, "添加成功");
    }

    @ApiOperation(value = "查看留言列表")
    @GetMapping("/messages")
    public Result<List<MessageDTO>> listMessages() {
        return new Result<>(true, StatusConst.OK, "添加成功", messageService.listMessages());
    }

    @ApiOperation(value = "查看后台留言列表")
    @GetMapping("/admin/messages")
    public Result<PageDTO<MessageBackDTO>> listMessageBackDTO(ConditionVO condition) {
        return new Result<>(true, StatusConst.OK, "添加成功", messageService.listMessageBackDTO(condition));
    }

    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除留言")
    @DeleteMapping("/admin/messages")
    public Result deleteMessages(@RequestBody List<Integer> messageIdList) {
        messageService.removeByIds(messageIdList);
        return new Result<>(true, StatusConst.OK, "操作成功");
    }

}

