package com.robin.blog.dao;

import com.robin.blog.dto.ArticleSearchDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/14 10:01
 */
@Repository
public interface ElasticsearchDao extends ElasticsearchRepository<ArticleSearchDTO, Integer> {

}
