package com.robin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.robin.blog.dao.ArticleTagDao;
import com.robin.blog.dto.PageDTO;
import com.robin.blog.dto.TagDTO;
import com.robin.blog.domain.ArticleTag;
import com.robin.blog.domain.Tag;
import com.robin.blog.dao.TagDao;
import com.robin.blog.exception.ServeException;
import com.robin.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robin.blog.utils.BeanCopyUtil;
import com.robin.blog.vo.ConditionVO;
import com.robin.blog.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author robin
 * @since 2021-12-18
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {
    @Autowired
    private TagDao tagDao;
    @Autowired
    private ArticleTagDao articleTagDao;

    @Override
    public PageDTO<TagDTO> listTags() {
        // 查询标签列表
        List<Tag> tagList = tagDao.selectList(new LambdaQueryWrapper<Tag>().select(Tag::getId, Tag::getTagName));
        // 转换DTO
        List<TagDTO> tagDTOList = BeanCopyUtil.copyList(tagList, TagDTO.class);
        // 查询标签数量
        Integer count = tagDao.selectCount(null);
        return new PageDTO<>(tagDTOList, count);
    }

    @Override
    public PageDTO<Tag> listTagBackDTO(ConditionVO condition) {
        // 分页查询标签列表
        Page<Tag> page = new Page<>(condition.getCurrent(), condition.getSize());
        Page<Tag> tagPage = tagDao.selectPage(page, new LambdaQueryWrapper<Tag>()
                .select(Tag::getId, Tag::getTagName, Tag::getCreateTime)
                .like(StringUtils.isNotBlank(condition.getKeywords()), Tag::getTagName, condition.getKeywords())
                .orderByDesc(Tag::getCreateTime));
        return new PageDTO<>(tagPage.getRecords(), (int) tagPage.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTag(List<Integer> tagIdList) {
        //查询标签下是否有文章
        Integer count = articleTagDao.selectCount(new LambdaQueryWrapper<ArticleTag>()
                .in(ArticleTag::getTagId, tagIdList));
        if (count > 0) {
            throw new ServeException("删除失败，该标签下存在文章");
        }
        tagDao.deleteBatchIds(tagIdList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateTag(TagVO tagVO) {
        Integer count = tagDao.selectCount(new LambdaQueryWrapper<Tag>()
                .eq(Tag::getTagName, tagVO.getTagName()));
        if (count > 0) {
            throw new ServeException("标签名已存在");
        }
        Tag tag = Tag.builder()
                .id(tagVO.getId())
                .tagName(tagVO.getTagName())
                .createTime(Objects.isNull(tagVO.getId()) ? new Date() : null)
                .build();
        this.saveOrUpdate(tag);
    }

}
