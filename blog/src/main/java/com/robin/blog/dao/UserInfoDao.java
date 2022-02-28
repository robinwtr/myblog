package com.robin.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robin.blog.domain.UserInfo;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Repository;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/14 10:22
 */

@Repository
public interface UserInfoDao extends BaseMapper<UserInfo> {


}
