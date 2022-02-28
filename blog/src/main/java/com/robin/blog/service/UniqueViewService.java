package com.robin.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.robin.blog.domain.UniqueView;
import com.robin.blog.dto.UniqueViewDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 10:57
 */
@Repository
public interface UniqueViewService extends IService<UniqueView> {

    /**
     * 统计每日用户量
     */
    void saveUniqueView();

    /**
     * 获取7天用户量统计
     * @return 用户量
     */
    List<UniqueViewDTO> listUniqueViews();
}
