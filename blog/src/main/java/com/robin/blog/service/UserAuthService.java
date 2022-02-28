package com.robin.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.robin.blog.domain.UserAuth;
import com.robin.blog.dto.PageDTO;
import com.robin.blog.dto.UserBackDTO;
import com.robin.blog.dto.UserInfoDTO;
import com.robin.blog.vo.ConditionVO;
import com.robin.blog.vo.PasswordVO;
import com.robin.blog.vo.UserVO;
import org.springframework.stereotype.Repository;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 11:01
 */
@Repository
public interface UserAuthService extends IService<UserAuth> {

    /**
     * 发送邮箱验证码
     * @param username 邮箱号
     */
    void sendCode(String username);

    /**
     * 用户注册1
     * @param userVO 用户对象
     */
    void saveUser(UserVO userVO);


    /**
     * qq登录
     * @param openId
     * @param accessToken
     * @return 用户登录信息
     */
    UserInfoDTO qqLogin(String openId, String accessToken);

    /**
     * 微博登录
     * @param code
     * @return
     */
    UserInfoDTO weiBoLogin(String code);

    /**
     * 修改棉麻
     * @param userVO
     */
    void updatePassword(UserVO userVO);

    /**
     * 修改管理员密码
     * @param passwordVO
     */
    void updateAdminPassword(PasswordVO passwordVO);

    /**
     * 查询后台用户列表
     * @param conditionVO
     * @return
     */
    PageDTO<UserBackDTO> listUserBackDTO(ConditionVO conditionVO);

}
