package com.robin.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robin.blog.dao.ArticleTagDao;
import com.robin.blog.domain.ArticleTag;
import com.robin.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/17 13:48
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagDao, ArticleTag> implements ArticleTagService {
}
