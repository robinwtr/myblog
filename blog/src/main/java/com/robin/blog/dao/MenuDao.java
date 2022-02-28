package com.robin.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robin.blog.domain.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/14 10:03
 */
@Repository
public interface MenuDao extends BaseMapper<Menu> {

    /**
     * 根据用户id查询菜单
     * @param userInfoId 用户信息id
     * @return 菜单列表
     */
    List<Menu> listMenuByUserInfoId(Integer userInfoId);
}
