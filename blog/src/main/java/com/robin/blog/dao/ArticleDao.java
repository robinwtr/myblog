package com.robin.blog.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robin.blog.domain.Article;
import com.robin.blog.dto.*;
import com.robin.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/13 15:21
 */

@Repository
public interface ArticleDao extends BaseMapper<Article> {

    /**
     * 查询首页文章
     * @param current 当前页码
     * @return 首页文章集合
     */
    List<ArticleHomeDTO> listArticles(Long current);

    /**
     * 根据id查询文章
     * @param articleId 文章id
     * @return 文章
     */
    ArticleDTO getArticleById(Integer articleId);

    /**
     * 根据条件查询文章
     * @param condition 条件
     * @return 文章集合
     */
    List<ArticlePreviewDTO> listArticleByCondition(@Param("condition") ConditionVO condition);

    /**
     * 查询后台文章
     * @param condition
     * @return
     */
    List<ArticleBackDTO> listArticleBacks(@Param("condition") ConditionVO condition);

    /**
     * 查询后台文章总数
     * @param condition
     * @return
     */
    Integer countArticleBacks(@Param("condition") ConditionVO condition);

    /**
     * 查询文章排行
     * @param articleIdList
     * @return
     */
    List<Article> listArticleRank(@Param("articleIdList") List<Integer> articleIdList);

    /**
     * 查看文章的推荐文章
     * @param articleId
     * @return
     */
    List<ArticleRecommendDTO> listArticleRecommends(@Param("articleId") Integer articleId);
}
