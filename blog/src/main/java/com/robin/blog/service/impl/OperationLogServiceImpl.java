package com.robin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robin.blog.dao.OperationLogDao;
import com.robin.blog.dto.OperationLogDTO;
import com.robin.blog.dto.PageDTO;
import com.robin.blog.domain.OperationLog;
import com.robin.blog.service.OperationLogService;
import com.robin.blog.utils.BeanCopyUtil;
import com.robin.blog.vo.ConditionVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author: robin
 * @date: 2021-12-23
 **/
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogDao, OperationLog> implements OperationLogService {

    @Override
    public PageDTO<OperationLogDTO> listOperationLogs(ConditionVO conditionVO) {
        Page<OperationLog> page = new Page<>(conditionVO.getCurrent(), conditionVO.getSize());
        // 查询日志列表
        Page<OperationLog> operationLogPage = this.page(page, new LambdaQueryWrapper<OperationLog>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), OperationLog::getOptModule, conditionVO.getKeywords())
                .or()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), OperationLog::getOptDesc, conditionVO.getKeywords())
                .gt(Objects.nonNull(conditionVO.getStartTime()), OperationLog::getCreateTime, conditionVO.getStartTime())
                .lt(Objects.nonNull(conditionVO.getEndTime()), OperationLog::getCreateTime, conditionVO.getEndTime())
                .orderByDesc(OperationLog::getId));
        List<OperationLogDTO> operationLogDTOList = BeanCopyUtil.copyList(operationLogPage.getRecords(), OperationLogDTO.class);
        return new PageDTO<>(operationLogDTOList, (int) operationLogPage.getTotal());
    }

}
