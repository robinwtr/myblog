package com.robin.blog.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robin.blog.domain.Tag;
import org.springframework.stereotype.Repository;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/14 10:15
 */

@Repository
public interface TagDao extends BaseMapper<Tag> {
}
