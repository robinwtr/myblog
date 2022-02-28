package com.robin.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robin.blog.domain.UserRole;
import org.springframework.stereotype.Repository;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/14 10:23
 */

@Repository
public interface UserRoleDao extends BaseMapper<UserRole> {
}
