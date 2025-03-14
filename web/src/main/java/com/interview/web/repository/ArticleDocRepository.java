package com.interview.web.repository;

import com.interview.web.pojo.entity.ArticleDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleDocRepository extends ElasticsearchRepository<ArticleDoc, Integer> {
}
