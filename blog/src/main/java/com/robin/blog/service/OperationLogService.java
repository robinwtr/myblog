package com.robin.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.robin.blog.domain.OperationLog;
import com.robin.blog.dto.OperationLogDTO;
import com.robin.blog.dto.PageDTO;
import com.robin.blog.vo.ConditionVO;
import org.springframework.stereotype.Repository;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 10:17
 */
@Repository
public interface OperationLogService extends IService<OperationLog> {

    /**
     * 查询日志列表
     * @param conditionVO 条件
     * @return 日志列表
     */
    PageDTO<OperationLogDTO> listOperationLogs(ConditionVO conditionVO);
}
