package com.interview.web.service;

import com.interview.web.pojo.param.FullTextSearchDto;
import com.interview.web.pojo.param.QuickSearchDto;
import com.interview.web.pojo.param.SearchCountDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author qikun
 * @date 3/14/2025  6:29 PM
 */
public interface SearchService {
    /**
     * 全站检索
     * @param fullTextSearch
     */
    Page searchByType(FullTextSearchDto fullTextSearch, Integer memberId);

    /**
     * 全站检索返回各类型的数量
     * @param fullTextSearch
     */
    SearchCountDto count(FullTextSearchDto fullTextSearch);
    /**
     * 检索词分词
     */
    List<String> split(String content);

    /**
     * 快速检索
     * @param content
     */
    List<QuickSearchDto> quickSearch(String content);
}
