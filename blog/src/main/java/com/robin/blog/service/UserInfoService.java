package com.robin.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.robin.blog.domain.UserInfo;
import com.robin.blog.dto.PageDTO;
import com.robin.blog.dto.UserOnlineDTO;
import com.robin.blog.vo.ConditionVO;
import com.robin.blog.vo.EmailVO;
import com.robin.blog.vo.UserInfoVO;
import com.robin.blog.vo.UserRoleVO;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 11:12
 */
@Repository
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 修改用户资料
     * @param userInfoVO 用户资料
     */
    void updateUserInfo(UserInfoVO userInfoVO);

    /**
     * 修改用户头像
     *
     * @param file 头像图片
     * @return 头像OSS地址
     */
    String updateUserAvatar(MultipartFile file);

    /**
     * 绑定用户邮箱
     * @param emailVO 邮箱
     */
    void saveUserEmail(EmailVO emailVO);

    /**
     * 修改用户权限
     *
     * @param userRoleVO 用户权限
     */
    void updateUserRole(UserRoleVO userRoleVO);

    /**
     * 修改用户禁用状态
     *
     * @param userInfoId 用户信息id
     * @param isDisable  禁用状态
     */
    void updateUserDisable(Integer userInfoId, Integer isDisable);

    /**
     * 查看在线用户列表
     * @param conditionVO 条件
     * @return 在线用户列表
     */
    PageDTO<UserOnlineDTO> listOnlineUsers(ConditionVO conditionVO);

    /**
     * 下线用户
     * @param userInfoId 用户信息id
     */
    void removeOnlineUser(Integer userInfoId);
}
