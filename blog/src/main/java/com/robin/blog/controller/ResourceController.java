package com.robin.blog.controller;

import com.robin.blog.constant.StatusConst;
import com.robin.blog.dto.ResourceDTO;
import com.robin.blog.dto.labelOptionDTO;
import com.robin.blog.service.ResourceService;
import com.robin.blog.vo.ResourceVO;
import com.robin.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: robin
 * @date: 2021-12-27
 **/
@Api(tags = "资源模块")
@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "导入swagger接口")
    @GetMapping("/admin/resources/import/swagger")
    public Result importSwagger() {
        resourceService.importSwagger();
        return new Result<>(true, StatusConst.OK, "导入成功");
    }

    @ApiOperation(value = "查看资源列表")
    @GetMapping("/admin/resources")
    public Result<List<ResourceDTO>> listResources() {
        return new Result<>(true, StatusConst.OK, "查询成功", resourceService.listResource());
    }

    @ApiOperation(value = "删除资源")
    @DeleteMapping("/admin/resources")
    public Result deleteResources(@RequestBody List<Integer> resourceIdList) {
        resourceService.deleteResources(resourceIdList);
        return new Result<>(true, StatusConst.OK, "删除成功");
    }

    @ApiOperation(value = "新增或修改资源")
    @PostMapping("/admin/resources")
    public Result saveOrUpdateResource(@RequestBody @Valid ResourceVO resourceVO) {
        resourceService.saveOrUpdateResource(resourceVO);
        return new Result<>(true, StatusConst.OK, "操作成功");
    }

    @ApiOperation(value = "查看角色资源选项")
    @GetMapping("/admin/role/resources")
    public Result<List<labelOptionDTO>> listResourceOption() {
        return new Result<>(true, StatusConst.OK, "查询成功", resourceService.listResourceOption());
    }


}
