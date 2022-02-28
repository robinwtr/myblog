package com.robin.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robin.blog.domain.Category;
import com.robin.blog.dto.CategoryDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/14 9:38
 */
@Repository
public interface CategoryDao extends BaseMapper<Category> {

    /**
     * 查询分类和对应文章数量
     * @return 分类集合
     */
    List<CategoryDTO> listCategoryDTO();
}
