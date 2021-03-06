package com.robin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robin.blog.dao.ArticleDao;
import com.robin.blog.dao.CategoryDao;
import com.robin.blog.domain.Article;
import com.robin.blog.domain.Category;
import com.robin.blog.dto.CategoryDTO;
import com.robin.blog.dto.PageDTO;
import com.robin.blog.exception.ServeException;
import com.robin.blog.service.CategoryService;
import com.robin.blog.vo.CategoryVO;
import com.robin.blog.vo.ConditionVO;
import org.elasticsearch.common.recycler.Recycler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Action;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 14:56
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ArticleDao articleDao;


    @Override
    public PageDTO<CategoryDTO> listCategories() {
        return new PageDTO<>(categoryDao.listCategoryDTO(),categoryDao.selectCount(null));
    }

    @Override
    public PageDTO<Category> listCategoryBackDTO(ConditionVO conditionVO) {
        //分页查询分类列表
        Page<Category> page = new Page<>(conditionVO.getCurrent(), conditionVO.getSize());
        Page<Category> categoryPage = categoryDao.selectPage(page, new LambdaQueryWrapper<Category>()
                .select(Category::getId, Category::getCategoryName, Category::getCreateTime)
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Category::getCategoryName, conditionVO.getKeywords())
                .orderByDesc(Category::getId));
        return new PageDTO<>(categoryPage.getRecords(), (int) categoryPage.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCategory(List<Integer> categoryList) {
        //查询分类id下是否有文章
        Integer count = articleDao.selectCount(new LambdaQueryWrapper<Article>()
                .in(Article::getCategoryId, categoryList));
        if (count > 0){
            throw new ServeException("删除失败，该分类下存在文章");
        }
        categoryDao.deleteBatchIds(categoryList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateCategory(CategoryVO categoryVO) {
        //判断分类名重复
        Integer count = categoryDao.selectCount(new LambdaQueryWrapper<Category>()
                .eq(Category::getCategoryName, categoryVO.getCategoryName()));
        if (count > 0) {
            throw new ServeException("分类名已存在");
        }
        Category category = Category.builder()
                .id(categoryVO.getId())
                .categoryName(categoryVO.getCategoryName())
                .createTime(Objects.isNull(categoryVO.getId()) ? new Date() : null)
                .build();
        this.saveOrUpdate(category);
    }
}
