package com.robin.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robin.blog.domain.Role;
import com.robin.blog.dto.RoleDTO;
import com.robin.blog.dto.UrlRoleDTO;
import com.robin.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/14 10:08
 */

@Repository
public interface RoleDao extends BaseMapper<Role> {

    /**
     * 查询路由角色列表
     * @return 角色
     */
    List<UrlRoleDTO> listUrlRoles();

    /**
     * 根据用户id获取角色列表
     * @param userInfoId 用户id
     * @return 角色标签
     */
    List<String> listRoleByUserInfoId(Integer userInfoId);

    /**
     * 查询角色列表
     * @param conditionVO 条件
     * @return 角色列表
     */
    List<RoleDTO> listRoles(@Param("conditionVO") ConditionVO conditionVO);
}
