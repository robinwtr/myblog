package com.robin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robin.blog.dao.ArticleDao;
import com.robin.blog.dao.ArticleTagDao;
import com.robin.blog.dao.CategoryDao;
import com.robin.blog.dao.TagDao;
import com.robin.blog.domain.Article;
import com.robin.blog.domain.ArticleTag;
import com.robin.blog.domain.Category;
import com.robin.blog.domain.Tag;
import com.robin.blog.dto.*;
import com.robin.blog.service.ArticleService;
import com.robin.blog.service.ArticleTagService;
import com.robin.blog.utils.BeanCopyUtil;
import com.robin.blog.utils.UserUtil;
import com.robin.blog.vo.ArticleVO;
import com.robin.blog.vo.ConditionVO;
import com.robin.blog.vo.DeleteVO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
import java.util.stream.Collectors;

import static com.robin.blog.constant.CommonConst.FALSE;
import static com.robin.blog.constant.RedisPrefixConst.*;


/**
 * @author robin
 * @version 1.0
 * @date 2021/12/14 11:09
 */

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private ArticleTagDao articleTagDao;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleTagService articleTagService;


    @Override
    public PageDTO<ArchiveDTO> listArchives(Long current) {
        Page<Article> page = new Page<>(current, 10);
        Page<Article> articlePage = articleDao.selectPage(page, new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getCreateTime)
                .orderByDesc(Article::getCreateTime)
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getIsDraft, FALSE));
        List<ArchiveDTO> archiveDTOList = BeanCopyUtil.copyList(articlePage.getRecords(), ArchiveDTO.class);

        return new PageDTO<>(archiveDTOList, (int) articlePage.getTotal());
    }

    @Override
    public PageDTO<ArticleBackDTO> listArticleBackDTO(ConditionVO condition) {
        //转换页码
        condition.setCurrent((condition.getCurrent()-1)*condition.getSize());
        //查询文章总量
        Integer count = articleDao.countArticleBacks(condition);
        if (count == 0) return new PageDTO<>();
        //查询后台文章
        List<ArticleBackDTO> articleBackDTOList = articleDao.listArticleBacks(condition);
        Map<String, Integer> viewsCountMap = redisTemplate.boundHashOps(ARTICLE_VIEWS_COUNT).entries();
        Map<String, Integer> likeCountMap = redisTemplate.boundHashOps(ARTICLE_LIKE_COUNT).entries();
        //查询文章点赞量和浏览量封装点赞量和浏览量
        articleBackDTOList.forEach(item ->{
            item.setViewsCount(Objects.requireNonNull(viewsCountMap).get(item.getId().toString()));
            item.setLikeCount(Objects.requireNonNull(likeCountMap).get(item.getId().toString()));
        });
        return new PageDTO<>(articleBackDTOList, count);
    }

    @Override
    public List<ArticleHomeDTO> listArticles(Long current) {
        //转换页码分页查询文章
        List<ArticleHomeDTO> articleHomeDTOList = articleDao.listArticles((current - 1) * 10);
        return articleHomeDTOList;
    }

    @Override
    public ArticlePreviewListDTO listArticleByCondition(ConditionVO condition) {
        //转换页码
        condition.setCurrent((condition.getCurrent() - 1) * 9);
        //搜索条件对应数据
        List<ArticlePreviewDTO> articlePreviewDTOList = articleDao.listArticleByCondition(condition);
        //搜索条件对应名(标签或分类名)
        String name;
        if (Objects.nonNull(condition.getCategoryId())) {
            name = categoryDao.selectOne(new LambdaQueryWrapper<Category>()
            .select(Category::getCategoryName)
            .eq(Category::getId, condition.getCategoryId())).getCategoryName();
        } else {
            name = tagDao.selectOne(new LambdaQueryWrapper<Tag>()
            .select(Tag::getTagName)
            .eq(Tag::getId, condition.getTagId())).getTagName();
        }

        return ArticlePreviewListDTO.builder()
                .articlePreviewDTOList(articlePreviewDTOList)
                .name(name)
                .build();
    }

    @Override
    public List<ArticleSearchDTO> listArticleBySearch(ConditionVO condition) {
        return searchArticle(buildQuery(condition));
    }

    @Override
    public ArticleVO getArticleBackById(Integer articleId) {
        // 查询文章信息
        Article article = articleDao.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getArticleContent, Article::getArticleCover, Article::getCategoryId, Article::getIsTop, Article::getIsDraft)
                .eq(Article::getId, articleId));
        // 查询文章标签
        List<Integer> tagIdList = articleTagDao.selectList(new LambdaQueryWrapper<ArticleTag>()
                .select(ArticleTag::getTagId)
                .eq(ArticleTag::getArticleId, article.getId()))
                .stream()
                .map(ArticleTag::getTagId).collect(Collectors.toList());
        return ArticleVO.builder()
                .id(article.getId())
                .articleTitle(article.getArticleTitle())
                .articleContent(article.getArticleContent())
                .articleCover(article.getArticleCover())
                .categoryId(article.getCategoryId())
                .isTop(article.getIsTop())
                .tagIdList(tagIdList)
                .isDraft(article.getIsDraft())
                .build();
    }

    @Override
    public ArticleDTO getArticleById(Integer articleId) {
        //更新文章浏览量
        updateArticleViewsCount(articleId);
        //查询id对应的文章
        ArticleDTO articleDTO = articleDao.getArticleById(articleId);
        //查询上一篇或下一篇文章
        Article lastArticle = articleDao.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getId,Article::getArticleTitle, Article::getArticleCover)
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getIsDraft, FALSE)
                .gt(Article::getId, articleId)
                .orderByAsc(Article::getId)
                .last("limit 1"));
        Article nextArticle = articleDao.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getId,Article::getArticleTitle, Article::getArticleCover)
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getIsDraft, FALSE)
                .gt(Article::getId, articleId)
                .orderByAsc(Article::getId)
                .last("limit 1"));
        articleDTO.setLastArticle(BeanCopyUtil.copyObject(lastArticle, ArticlePaginationDTO.class));
        articleDTO.setNextArticle(BeanCopyUtil.copyObject(nextArticle, ArticlePaginationDTO.class));
        //查询相关推荐文章
        articleDTO.setArticleRecommendDTOList(articleDao.listArticleRecommends(articleId));
        //封装点赞量和浏览量
        articleDTO.setViewsCount((Integer) redisTemplate.boundHashOps(ARTICLE_VIEWS_COUNT).get(articleId.toString()));
        articleDTO.setLikeCount((Integer) redisTemplate.boundHashOps(ARTICLE_LIKE_COUNT).get(articleId.toString()));

        return articleDTO;
    }

    @Override
    public List<ArticleRecommendDTO> listNewestArticles() {
        //查询最新文章
        List<Article> articleList = articleDao.selectList(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getArticleCover, Article::getCreateTime)
                .eq(Article::getIsDraft, FALSE)
                .eq(Article::getIsDelete, FALSE)
                .orderByDesc(Article::getId)
                .last("limit 5"));
        return  BeanCopyUtil.copyList(articleList, ArticleRecommendDTO.class);
    }

    @Override
    public ArticleOptionDTO listArticleOptionDTO() {
        //查询文章分类选项
        List<Category> categoryList = categoryDao.selectList(new LambdaQueryWrapper<Category>()
                .select(Category::getId, Category::getCategoryName));
        List<CategoryBackDTO> categoryBackDTOList = BeanCopyUtil.copyList(categoryList,CategoryBackDTO.class);
        //查询文章标签选项
        List<Tag> tagList = tagDao.selectList(new LambdaQueryWrapper<Tag>()
                .select(Tag::getId, Tag::getTagName));
        List<TagDTO> tagDTOList = BeanCopyUtil.copyList(tagList, TagDTO.class);

        return ArticleOptionDTO.builder()
                .categoryBackDTOList(categoryBackDTOList)
                .tagDTOList(tagDTOList)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveArticleLike(Integer articleId) {
        //查询当前用户点赞过的文章id
        Set<Integer> articleLikeSet = (Set<Integer>) redisTemplate.boundHashOps(ARTICLE_USER_LIKE).get(UserUtil.getLoginUser().getUserInfoId().toString());
        //第一次点赞则创建
        if (CollectionUtils.isEmpty(articleLikeSet)) articleLikeSet = new HashSet<>();
        if (articleLikeSet.contains(articleId)) {
            articleLikeSet.remove(articleId);
            redisTemplate.boundHashOps(ARTICLE_LIKE_COUNT).increment(articleId.toString(), -1);
        } else {
            articleLikeSet.add(articleId);
            redisTemplate.boundHashOps(ARTICLE_LIKE_COUNT).increment(articleId.toString(),1);
        }
        //保存记录
        redisTemplate.boundHashOps(ARTICLE_USER_LIKE).put(UserUtil.getLoginUser().getUserInfoId().toString(), articleLikeSet);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateArticle(ArticleVO articleVO) {
        //保存或修改文章
        Article article = Article.builder()
                .id(articleVO.getId())
                .userId(UserUtil.getLoginUser().getUserInfoId())
                .categoryId(articleVO.getCategoryId())
                .articleCover(articleVO.getArticleCover())
                .articleTitle(articleVO.getArticleTitle())
                .articleContent(articleVO.getArticleContent())
                .createTime(Objects.isNull(articleVO.getId()) ? new Date() : null)
                .updateTime(Objects.isNull(articleVO.getId()) ? new Date() : null)
                .isTop(articleVO.getIsTop())
                .isDraft(articleVO.getIsDraft())
                .build();
        articleService.saveOrUpdate(article);
        //编辑文章则删除文章所有标签
        if (Objects.nonNull(articleVO.getId()) && articleVO.getIsDraft().equals(FALSE)) {
            articleTagDao.delete(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, articleVO.getId()));
        }
        //添加文章标签
        if (!articleVO.getTagIdList().isEmpty()) {
            List<ArticleTag> articleTagList = articleVO.getTagIdList().stream().map(tagId -> ArticleTag.builder()
                    .articleId(article.getId())
                    .tagId(tagId)
                    .build()).collect(Collectors.toList());
            articleTagService.saveBatch(articleTagList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateArticleTop(Integer articleId, Integer isTop) {
        //修改文章置顶
        Article article = Article.builder()
                .id(articleId)
                .isTop(isTop)
                .build();
        articleDao.updateById(article);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateArticleDelete(DeleteVO deleteVO) {
        //修改文章逻辑删除状态
        List<Article> articleList = deleteVO.getIdList().stream().map(id ->Article.builder()
                .id(id)
                .isTop(FALSE)
                .isDelete(deleteVO.getIsDelete())
                .build()).collect(Collectors.toList());
        articleService.updateBatchById(articleList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteArticles(List<Integer> articleIdList) {
        //删除文章标签失联
        articleTagDao.delete(new LambdaQueryWrapper<ArticleTag>().in(ArticleTag::getArticleId, articleIdList));
        //删除文章
        articleDao.deleteBatchIds(articleIdList);
    }

    /**
     * 更新文章浏览量
     * @param articleId 文章id
     */
    public void updateArticleViewsCount(Integer articleId) {
        //判断是否是第一次访问，增加浏览量
        Set<Integer> set = (Set<Integer>) httpSession.getAttribute("articleSet");
        if (Objects.isNull(set)) set = new HashSet<>();
        if (!set.contains(articleId)) {
            set.add(articleId);
            httpSession.setAttribute("articleSet", set);
            //浏览量++
            redisTemplate.boundHashOps(ARTICLE_VIEWS_COUNT).increment(articleId.toString(), 1);
        }
    }

    /**
     * 搜索文章构造
     * @param condition 条件
     * @return es条件构造器
     */
    private NativeSearchQueryBuilder buildQuery(ConditionVO condition) {
        //条件构造器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //根据关键词搜索文章标题或内容
        if (Objects.nonNull(condition.getKeywords())) {
            boolQueryBuilder.must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("articleTitle", condition.getKeywords()))
                    .should(QueryBuilders.matchQuery("articleContent", condition.getKeywords())))
                    .must(QueryBuilders.termQuery("isDelete", FALSE));
        }
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        return  nativeSearchQueryBuilder;
    }

    private List<ArticleSearchDTO> searchArticle(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        //添加文章标题高亮
        HighlightBuilder.Field titleField = new HighlightBuilder.Field("articleTitle");
        titleField.preTags("<span> style='color:#f47466'>");
        titleField.postTags("</span>");
        // 添加文章内容高亮
        HighlightBuilder.Field contentField = new HighlightBuilder.Field("articleContent");
        contentField.preTags("<span style='color:#f47466'>");
        contentField.postTags("</span>");
        contentField.fragmentSize(200);
        nativeSearchQueryBuilder.withHighlightFields(titleField, contentField);
        // 搜索
        SearchHits<ArticleSearchDTO> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), ArticleSearchDTO.class);
        return search.getSearchHits().stream().map(hit -> {
            ArticleSearchDTO article = hit.getContent();
            // 获取文章标题高亮数据
            List<String> titleHighLightList = hit.getHighlightFields().get("articleTitle");
            if (CollectionUtils.isNotEmpty(titleHighLightList)) {
                // 替换标题数据
                article.setArticleTitle(titleHighLightList.get(0));
            }
            // 获取文章内容高亮数据
            List<String> contentHighLightList = hit.getHighlightFields().get("articleContent");
            if (CollectionUtils.isNotEmpty(contentHighLightList)) {
                // 替换内容数据
                article.setArticleContent(contentHighLightList.get(0));
            }
            return article;
        }).collect(Collectors.toList());
    }

}
