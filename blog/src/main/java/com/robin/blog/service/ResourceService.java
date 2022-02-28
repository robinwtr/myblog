package com.robin.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.robin.blog.domain.Resource;
import com.robin.blog.dto.ResourceDTO;
import com.robin.blog.dto.labelOptionDTO;
import com.robin.blog.vo.ResourceVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 10:19
 */
@Repository
public interface ResourceService extends IService<Resource> {

    /**
     * 导入swagger权限
     */
    void importSwagger();

    /**
     * 添加或修改资源
     * @param resourceVO 资源对象
     */
    void saveOrUpdateResource(ResourceVO resourceVO);

    /**
     * 删除资源
     * @param resourceIdList 资源id集合
     */
    void deleteResources(List<Integer> resourceIdList);

    /**
     * 查看资源列表
     * @return 集合
     */
    List<ResourceDTO> listResource();

    /**
     * 查看资源选项
     * @return 资源选项
     */
    List<labelOptionDTO> listResourceOption();
}
