package com.robin.blog.service;

import com.robin.blog.dto.BlogBackInfoDTO;
import com.robin.blog.dto.BlogHomeInfoDTO;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 9:17
 */
public interface BlogInfoService {

    /**
     * 获取网页数据
     * @return 博客首页信息
     */
    BlogHomeInfoDTO getBlogInfo();

    /**
     * 获取后台首页数据
     * @return 博客后台信息
     */
    BlogBackInfoDTO getBlogBackInfo();

    /**
     * 获取关于我的内容
     * @return 获取关于我的内容
     */
    String getAbout();

    /**
     * 修改关于我的内容
     * @param aboutContent 关于我的内容
     */
    void updateAbout(String aboutContent);

    /**
     * 修改公告
     * @param notice
     */
    void updateNotice(String notice);

    /**
     * 后台查看公告
     * @return 公告
     */
    String getNotice();
}
