package com.robin.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robin.blog.dao.UserInfoDao;
import com.robin.blog.dao.UserRoleDao;
import com.robin.blog.domain.UserRole;
import com.robin.blog.enums.RoleEnum;
import com.robin.blog.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: robin
 * @date: 2021-12-27
 **/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {


}
