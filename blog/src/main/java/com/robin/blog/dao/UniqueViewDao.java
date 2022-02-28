package com.robin.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robin.blog.domain.UniqueView;
import com.robin.blog.dto.UniqueViewDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/14 10:16
 */

@Repository
public interface UniqueViewDao extends BaseMapper<UniqueView> {

    /**
     * 获取7天用户量
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 用户集合
     */
    List<UniqueViewDTO> listUniqueViews(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
