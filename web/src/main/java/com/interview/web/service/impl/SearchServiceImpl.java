package com.interview.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.interview.web.constants.Const;
import com.interview.web.constants.EsType;
import com.interview.web.constants.WebConsts;
import com.interview.web.pojo.entity.ArticleDoc;
import com.interview.web.pojo.param.FullTextSearchDto;
import com.interview.web.pojo.param.QuickSearchDto;
import com.interview.web.pojo.param.SearchCountDto;
import com.interview.web.repository.ArticleDocRepository;
import com.interview.web.repository.MyResultMapper;
import com.interview.web.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

/**
 * @author haoxuexin
 */
@Service("searchService")
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private MyResultMapper myResultMapper;

    @Resource
    private ArticleDocRepository articleDocRepository;

    /**
     * 根据类型全站检索
     * @param fullTextSearch
     * @param loginMemberId
     */
    @Override
    public Page searchByType(FullTextSearchDto fullTextSearch, Integer loginMemberId) {
        loginMemberId = loginMemberId == null ? 0 : loginMemberId;
        // 构建searchQuery对象
        SearchQuery searchQuery = buildSearchQuery(fullTextSearch, true);
        Page data;
        try {
            //查询数据
            data = elasticsearchTemplate.queryForPage(searchQuery, getTypeClass(fullTextSearch.getType()), myResultMapper);
        } catch (Exception e) {
            log.error("--------------------------getting ElasticSearch date failed, error: {}", e);
            throw new RuntimeException(e);
        }
        log.info("=========================getting ElasticSearch data complete, search key: {}, return docs count: {}",
                fullTextSearch.getContent(),
                data.getContent().size());
        return data;
    }

    /**
     * 构建检索对象
     * @param fullTextSearch
     * @param pageable
     */
    private SearchQuery buildSearchQuery(FullTextSearchDto fullTextSearch, Boolean pageable) {
        Pageable page = PageRequest.of(fullTextSearch.getPageNum() == null ? 0 : fullTextSearch.getPageNum() - 1,
                fullTextSearch.getPageSize() == null ? 10 : fullTextSearch.getPageSize());
        // 要检索的字段及内容
        QueryBuilder queryBuilder;
        // 过滤构造
        QueryBuilder filterBuilder = null;
        // 要高亮显示的字段
        String[] highLightFields;
        // 查询的enum
        EsType esType = EsType.getEsTypeByKey(fullTextSearch.getType());
        if(null == esType) {
            //检索类型为空
            // new NotFountException(APICodeEnum.ES_TYPE_NOT_EXISTS);
        }
        switch (esType){
            case ARTICLE://文章
            case QUESTION://问题
            case GROUPTOPIC://话题
                //检索字段
                queryBuilder = multiMatchQuery(fullTextSearch.getContent(), "title", "content", "description", "authorName");
                //高亮字段
                highLightFields = new String[]{"title", "content", "description", "authorName"};
                filterBuilder = QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("status", "1"));
                break;
            case ARTICLE_CATE:
                //检索字段
                queryBuilder = multiMatchQuery(fullTextSearch.getContent(), "name");
                //高亮字段
                highLightFields = new String[]{"name"};
                filterBuilder = QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("status", "1"));
                break;
            case ANSWER:
                //检索字段
                queryBuilder = multiMatchQuery(fullTextSearch.getContent(), "content");
                //高亮字段
                highLightFields = new String[]{"content"};
                filterBuilder = QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("status", "1"));
                break;
            case GROUP:
                //检索字段
                queryBuilder = multiMatchQuery(fullTextSearch.getContent(), "name", "description");
                //高亮字段
                highLightFields = new String[]{"name", "description"};
                filterBuilder = QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("status", "1"));
                break;
            default:
                throw new RuntimeException();
                //throw new NotFountException(APICodeEnum.ES_TYPE_NOT_EXISTS);
        }
        // 设置多字段高亮
        List<HighlightBuilder.Field> lst = new ArrayList<>();
        Arrays.asList(highLightFields).forEach(item -> {
            HighlightBuilder.Field tem = new HighlightBuilder.Field(item);
            tem.preTags(WebConsts.HIGHLIGHT_PRE_TAG).postTags(WebConsts.HIGHLIGHT_POST_TAG);
            lst.add(tem);
        });
        HighlightBuilder.Field[] highLightArr = new HighlightBuilder.Field[lst.size()];
        for(int i = 0; i < lst.size() ; i++) {
            highLightArr[i] = lst.get(i);
        }
        if (pageable) {
            return new NativeSearchQueryBuilder()
                    .withIndices(Const.ES_SNS_INDEX)
                    .withTypes(fullTextSearch.getType())
                    .withQuery(queryBuilder)
                    .withFilter(filterBuilder)
                    .withPageable(page)
                    // 暂时取消高亮标签
//                    .withHighlightFields(highLightArr)
                    .build();
        }
        return new NativeSearchQueryBuilder()
                .withIndices(Const.ES_SNS_INDEX)
                .withTypes(fullTextSearch.getType())
                .withQuery(queryBuilder)
                .withFilter(filterBuilder)
                .build();
    }

    /**
     * 计数
     * @param fullTextSearch
     */
    @Override
    public SearchCountDto count(FullTextSearchDto fullTextSearch) {
        Map<String, Integer> map = new HashMap<>();
        //文章count
        fullTextSearch.setType(EsType.ARTICLE.getKey());
        map.put(EsType.ARTICLE.getKey(),
                (int) elasticsearchTemplate.count(buildSearchQuery(fullTextSearch, false), ArticleDoc.class));

        if (map.size() == 0) {
            return null;
        }
        //返回数据
        SearchCountDto dto = new SearchCountDto();
        dto.setArticleCount(map.get(EsType.ARTICLE.getKey()) == null ? 0 : map.get(EsType.ARTICLE.getKey()));//文章计数
        dto.setQaCount(map.get(EsType.QUESTION.getKey()) == null ? 0 : map.get(EsType.QUESTION.getKey()));//问答计数
        dto.setGroupTopicCount(map.get(EsType.GROUPTOPIC.getKey()) == null ? 0 : map.get(EsType.GROUPTOPIC.getKey()));//话题计数
        return dto;
    }

    /**
     * 同步es数据
     * @param type
     */
    public void sync(String type) {
        //同步es
        if(type.equals("article")) {
            //删除es中的文章
            articleDocRepository.deleteAll();
        }
    }

    /**
     * 分词
     * @param content
     */
    @Override
    public List<String> split(String content) {
        List<String> searchTermList = new ArrayList<>();
        if(!StrUtil.isBlank(content)){
            //分词
            AnalyzeRequestBuilder ikRequest = new AnalyzeRequestBuilder(
                    elasticsearchTemplate.getClient(), AnalyzeAction.INSTANCE,Const.ES_SNS_INDEX, content);
            ikRequest.setTokenizer(Const.ES_SEARCH_ANALYZER);
            List<AnalyzeResponse.AnalyzeToken> ikTokenList = ikRequest.execute().actionGet().getTokens();
            ikTokenList.forEach(ikToken -> searchTermList.add(ikToken.getTerm()));
        }
        //返回结果
        return searchTermList;
    }

    /**
     * 快速检索
     * @param content
     */
    @Override
    public List<QuickSearchDto> quickSearch(String content) {
        // 要检索的字段及内容
        QueryBuilder queryBuilder = multiMatchQuery(content, "title");
        QueryBuilder filterBuilder = QueryBuilders.boolQuery().filter(QueryBuilders.termsQuery("status", WebConsts.AUDIT_STATUS));
        SearchQuery searchQuery =  new NativeSearchQueryBuilder()
                .withIndices(Const.ES_SNS_INDEX)
                .withTypes(EsType.ARTICLE.getKey(), EsType.GROUPTOPIC.getKey(), EsType.QUESTION.getKey())
                .withQuery(queryBuilder)
                .withFilter(filterBuilder)
                .build();
        List<QuickSearchDto> searchRes = elasticsearchTemplate.queryForList(searchQuery, QuickSearchDto.class);
        //每个类型取2条数据
        List<QuickSearchDto> res = new ArrayList<>();
        if (!CollectionUtil.isEmpty(searchRes)) {
            List<QuickSearchDto> articleRes = new ArrayList<>();
            List<QuickSearchDto> groupTopicRes = new ArrayList<>();
            List<QuickSearchDto> questionRes = new ArrayList<>();
            searchRes.forEach(item -> {
                EsType type = EsType.getEsTypeByKey(item.getType());
                switch (type) {
                    case ARTICLE://文章
                        articleRes.add(item);
                        break;
                    case GROUPTOPIC://话题
                        groupTopicRes.add(item);
                        break;
                    case QUESTION://问题
                        questionRes.add(item);
                        break;
                    default:
                        break;
                }
            });
            res.addAll(articleRes.stream().limit(2).collect(Collectors.toList()));//文章
            res.addAll(groupTopicRes.stream().limit(2).collect(Collectors.toList()));//话题
            res.addAll(questionRes.stream().limit(2).collect(Collectors.toList()));//问题
        }else {
            res = Collections.emptyList();
        }
        //返回结果
        return res;
    }

    /**
     * 获取es类型
     * @param type
     */
    private Class getTypeClass(String type) {
        EsType esType = EsType.getEsTypeByKey(type);
        if(null == esType) {
            throw new RuntimeException();
        }
        Class t;
        switch (esType){
            case ARTICLE://文章
                t = ArticleDoc.class;
                break;
            default:
                throw new RuntimeException();
        }
        //返回数据
        return t;
    }
}
