package com.robin.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robin.blog.domain.UserAuth;
import com.robin.blog.dto.UserBackDTO;
import com.robin.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/14 10:19
 */
@Repository
public interface UserAuthDao extends BaseMapper<UserAuth> {

    /**
     * 登录
     * @param userAuth
     * @return
     */
    UserAuth login(UserAuth userAuth);

    /**
     * 查询后台用户列表
     * @param condition 条件
     * @return 用户集合
     */
    List<UserBackDTO> listUsers(@Param("condition")ConditionVO condition);

    /**
     * 查询后台用户数量
     * @param condition 条件
     * @return 用户数量
     */
    Integer countUser(@Param("condition") ConditionVO condition);
}
