package com.robin.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robin.blog.dao.RoleResourceDao;
import com.robin.blog.domain.RoleResource;
import com.robin.blog.service.RoleResourceService;
import org.springframework.stereotype.Service;

/**
 * @author: robin
 * @date: 2021-12-27
 **/
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceDao, RoleResource> implements RoleResourceService {
}
