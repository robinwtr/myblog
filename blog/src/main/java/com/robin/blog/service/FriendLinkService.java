package com.robin.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.robin.blog.domain.FriendLink;
import com.robin.blog.dto.FriendLinkBackDTO;
import com.robin.blog.dto.FriendLinkDTO;
import com.robin.blog.dto.PageDTO;
import com.robin.blog.vo.ConditionVO;
import com.robin.blog.vo.FriendLinkVO;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 9:49
 */
@Repository
public interface FriendLinkService extends IService<FriendLink> {

    /**
     * 查看友链集合
     * @return 友链集合
     */
    List<FriendLinkDTO> listFriendLinks();

    /**
     * 查看后台友链集合
     * @param conditionVO 条件
     * @return 友链集合
     */
    PageDTO<FriendLinkBackDTO> listFriendLinkDTO(ConditionVO conditionVO);

    /**
     * 保存或更新友链
     * @param friendLinkVO
     */
    void saveOrUpdateFriendLink(FriendLinkVO friendLinkVO);
}
