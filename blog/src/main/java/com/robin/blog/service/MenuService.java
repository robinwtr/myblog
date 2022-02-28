package com.robin.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.robin.blog.domain.Menu;
import com.robin.blog.dto.MenuDTO;
import com.robin.blog.dto.UserMenuDTO;
import com.robin.blog.dto.labelOptionDTO;
import com.robin.blog.vo.ConditionVO;
import org.apache.catalina.User;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 10:02
 */
@Repository
public interface MenuService extends IService<Menu> {

    /**
     * 查看菜单列表
     * @param conditionVO 条件
     * @return 菜单列表
     */
    List<MenuDTO> listMenus(ConditionVO conditionVO);

    /**
     * 查看角色菜单选项
     * @return 角色菜单选项
     */
    List<labelOptionDTO> listMenuOptions();

    /**
     * 查看用户菜单
     * @return 菜单列表
     */
    List<UserMenuDTO> listUserMenus();
}
