package com.robin.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.robin.blog.constant.CommonConst;
import com.robin.blog.dao.*;
import com.robin.blog.domain.Article;
import com.robin.blog.domain.UserInfo;
import com.robin.blog.dto.*;
import com.robin.blog.service.BlogInfoService;
import com.robin.blog.service.UniqueViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.robin.blog.constant.CommonConst.FALSE;
import static com.robin.blog.constant.RedisPrefixConst.*;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 13:51
 */
@Service
public class BlogInfoServiceImpl implements BlogInfoService {
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UniqueViewService uniqueViewService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public BlogHomeInfoDTO getBlogInfo() {
        //查询博主信息
        UserInfo userInfo = userInfoDao.selectOne(new LambdaQueryWrapper<UserInfo>()
                .select(UserInfo::getAvatar, UserInfo::getNickname, UserInfo::getIntro)
                .eq(UserInfo::getId, CommonConst.BLOGGER_ID));
        //查询文章数量
        Integer articleCount = articleDao.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDraft,FALSE)
                .eq(Article::getIsDelete,FALSE));
        //查询分类数量
        Integer categoryCount = categoryDao.selectCount(null);
        //查询标签数量
        Integer tagCount = tagDao.selectCount(null);
        //查询广告
        Object value = redisTemplate.boundValueOps(NOTICE).get();
        String notice = Objects.nonNull(value) ? value.toString() : "发布你的第一片公告吧";
        //查询访问数量
        String viewCount = Objects.requireNonNull(redisTemplate.boundValueOps(BLOG_VIEWS_COUNT).get()).toString();

        return BlogHomeInfoDTO.builder()
                .nickname(userInfo.getNickname())
                .avatar(userInfo.getAvatar())
                .intro(userInfo.getIntro())
                .articleCount(articleCount)
                .categoryCount(categoryCount)
                .tagCount(tagCount)
                .notice(notice)
                .viewsCount(viewCount)
                .build();
    }

    @Override
    public BlogBackInfoDTO getBlogBackInfo() {
        //查询访问量
        Integer viewsCount = (Integer) redisTemplate.boundValueOps(BLOG_VIEWS_COUNT).get();
        //查询留言量
        Integer messageCount = messageDao.selectCount(null);
        //查询用户量
        Integer userCount = userInfoDao.selectCount(null);
        //查询文章量
        Integer articleCount = articleDao.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete,FALSE)
                .eq(Article::getIsDraft, FALSE));
        //查询7天用户量
        List<UniqueViewDTO> uniqueViewList = uniqueViewService.listUniqueViews();
        //查询分类数据
        List<CategoryDTO> categoryDTOList = categoryDao.listCategoryDTO();
        //查询redis访问量前五的文章
        Map<String, Integer> articleViewsMap = redisTemplate.boundHashOps(ARTICLE_VIEWS_COUNT).entries();
        //将文章进行排序
        List<Integer> articleIdList = Objects.requireNonNull(articleViewsMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(item -> Integer.valueOf(item.getKey()))
                .collect(Collectors.toList()));
        //提取前五篇文章
        int index = Math.min(articleIdList.size(), 5);
        articleIdList = articleIdList.subList(0,index);
        //文章为空直接返回
        if (articleIdList.isEmpty()) {
            return BlogBackInfoDTO.builder()
                    .viewsCount(viewsCount)
                    .messageCount(messageCount)
                    .userCount(userCount)
                    .articleCount(articleCount)
                    .categoryDTOList(categoryDTOList)
                    .uniqueViewDTOList(uniqueViewList)
                    .build();
        }
        //查询文章标题
        List<Article> articleList = articleDao.listArticleRank(articleIdList);
        //封装浏览量
        List<ArticleRankDTO> articleRankDTOList = articleList.stream().map(article -> ArticleRankDTO.builder()
                .articleTitle(article.getArticleTitle())
                .viewsCount(articleViewsMap.get(article.getId().toString()))
                .build()).collect(Collectors.toList());
        return BlogBackInfoDTO.builder()
                .viewsCount(viewsCount)
                .messageCount(messageCount)
                .userCount(userCount)
                .articleCount(articleCount)
                .categoryDTOList(categoryDTOList)
                .uniqueViewDTOList(uniqueViewList)
                .articleRankDTOList(articleRankDTOList)
                .build();
    }

    @Override
    public String getAbout() {
        Object value = redisTemplate.boundValueOps(ABOUT).get();
        return Objects.nonNull(value) ? value.toString() : "";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAbout(String aboutContent) {
        redisTemplate.boundValueOps(ABOUT).set(aboutContent);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateNotice(String notice) {
        redisTemplate.boundValueOps(NOTICE).set(notice);
    }

    @Override
    public String getNotice() {
        Object value = redisTemplate.boundValueOps(NOTICE).get();
        return Objects.nonNull(value) ? value.toString() : "发布你的第一篇公告吧";
    }
}
