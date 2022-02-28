package com.robin.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.robin.blog.domain.Tag;
import com.robin.blog.dto.PageDTO;
import com.robin.blog.dto.TagDTO;
import com.robin.blog.vo.ConditionVO;
import com.robin.blog.vo.TagVO;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 10:55
 */
public interface TagService extends IService<Tag> {

    /**
     * 查询标签列表
     *
     * @return 标签列表
     */
    PageDTO<TagDTO> listTags();

    /**
     * 查询后台标签
     *
     * @param condition 条件
     * @return 标签列表
     */
    PageDTO<Tag> listTagBackDTO(ConditionVO condition);

    /**
     * 删除标签
     *
     * @param tagIdList 标签id集合
     */
    void deleteTag(List<Integer> tagIdList);

    /**
     * 保存或更新标签
     * @param tagVO 标签
     */
    void saveOrUpdateTag(TagVO tagVO);
}
