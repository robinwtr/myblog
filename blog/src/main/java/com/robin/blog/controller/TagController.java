package com.robin.blog.controller;


import com.robin.blog.annotation.OptLog;
import com.robin.blog.constant.StatusConst;
import com.robin.blog.dto.ArticlePreviewListDTO;
import com.robin.blog.dto.PageDTO;
import com.robin.blog.dto.TagDTO;
import com.robin.blog.domain.Tag;
import com.robin.blog.service.ArticleService;
import com.robin.blog.service.TagService;
import com.robin.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.robin.blog.constant.OptTypeConst.REMOVE;
import static com.robin.blog.constant.OptTypeConst.SAVE_OR_UPDATE;


/**
 * @author robin
 * @since 2021-12-24
 */
@Api(tags = "标签模块")
@RestController
public class TagController {
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "查看标签列表")
    @GetMapping("/tags")
    public Result<PageDTO<TagDTO>> listTags() {
        return new Result<>(true, StatusConst.OK, "查询成功", tagService.listTags());
    }

    @ApiOperation(value = "查看分类下对应的文章")
    @GetMapping("/tags/{tagId}")
    public Result<ArticlePreviewListDTO> listArticlesByCategoryId(@PathVariable("tagId") Integer tagId, Integer current) {
        ConditionVO conditionVO = ConditionVO.builder()
                .tagId(tagId)
                .current(current)
                .build();
        return new Result<>(true, StatusConst.OK, "查询成功", articleService.listArticleByCondition(conditionVO));
    }

    @ApiOperation(value = "查看后台标签列表")
    @GetMapping("/admin/tags")
    public Result<PageDTO<Tag>> listTagBackDTO(ConditionVO condition) {
        return new Result<>(true, StatusConst.OK, "查询成功", tagService.listTagBackDTO(condition));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加或修改标签")
    @PostMapping("/admin/tags")
    public Result saveOrUpdateTag(@Valid @RequestBody TagVO tagVO) {
        tagService.saveOrUpdateTag(tagVO);
        return new Result<>(true, StatusConst.OK, "操作成功");
    }

    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除标签")
    @DeleteMapping("/admin/tags")
    public Result deleteTag(@RequestBody List<Integer> tagIdList) {
        tagService.deleteTag(tagIdList);
        return new Result<>(true, StatusConst.OK, "删除成功");
    }

}

