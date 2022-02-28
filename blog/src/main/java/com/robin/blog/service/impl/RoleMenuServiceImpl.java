package com.robin.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robin.blog.dao.RoleMenuDao;
import com.robin.blog.domain.RoleMenu;
import com.robin.blog.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * @author: robin
 * @date: 2021-12-27
 **/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenu> implements RoleMenuService {
}
