package com.robin.blog.service;

import com.robin.blog.domain.Category;
import com.robin.blog.dto.CategoryDTO;
import com.robin.blog.dto.PageDTO;
import com.robin.blog.vo.CategoryVO;
import com.robin.blog.vo.ConditionVO;
import jdk.nashorn.internal.ir.LiteralNode;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 9:23
 */
public interface CategoryService {

    /**
     * 查询分类列表
     * @return 分类列表
     */
    PageDTO<CategoryDTO> listCategories();

    /**
     * 查询后台分类
     * @param conditionVO 条件
     * @return 分类列表
     */
    PageDTO<Category> listCategoryBackDTO(ConditionVO conditionVO);

    /**
     * 删除分类
     * @param categoryList 分类id集合
     */
    void deleteCategory(List<Integer> categoryList);

    /**
     * 添加分类或修改分类
     * @param categoryVO 分类
     */
    void saveOrUpdateCategory(CategoryVO categoryVO);
}
