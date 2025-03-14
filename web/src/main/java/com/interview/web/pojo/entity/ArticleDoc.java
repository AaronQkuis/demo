package com.interview.web.pojo.entity;

import com.interview.web.constants.Const;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章存入es的实体
 * Created by hxx on 2020/5/22
 * @author haoxuexin
 */
@Data
@Document(indexName = "cosmo-sns", type = "article", shards = 3, replicas = 0, refreshInterval = "-1")
@Builder
public class ArticleDoc implements Serializable {

    @Id
    private Integer id;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date)
    private Date createTime;

    /**
     * 状态，0未审核，1已审核，-1驳回
     */
    @Field(type = FieldType.Keyword)
    private Integer status;

    /**
     * 文档标题
     */
    @Field(type = FieldType.Text, analyzer = Const.ES_ANALYZER, searchAnalyzer = Const.ES_SEARCH_ANALYZER)
    private String title;

    /**
     * 描述说明
     */
    @Field(type = FieldType.Text, analyzer = Const.ES_ANALYZER, searchAnalyzer = Const.ES_SEARCH_ANALYZER)
    private String description;

    /**
     * 内容
     */
    @Field(type = FieldType.Text, analyzer = Const.ES_ANALYZER, searchAnalyzer = Const.ES_SEARCH_ANALYZER)
    private String content;

    /**
     * 作者名
     */
    @Field(type = FieldType.Text, analyzer = Const.ES_ANALYZER, searchAnalyzer = Const.ES_SEARCH_ANALYZER)
    private String authorName;

    /**
     * 类型
     */
    private String type;

    public ArticleDoc(Integer id, Date createTime, Integer status, String title, String description, String content, String authorName, String type) {
        this.id = id;
        this.createTime = createTime;
        this.status = status;
        this.title = title;
        this.description = description;
        this.content = content;
        this.authorName = authorName;
        this.type = type;
    }

    public ArticleDoc(){}

}