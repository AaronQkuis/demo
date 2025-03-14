package com.interview.web.constants;

import org.apache.commons.lang3.StringUtils;

public enum EsType {
    WEIBO("weibo", "微博"),
    ARTICLE("article", "文章"),
    ARTICLE_CATE("cate", "专栏"),
    GROUP("group", "群组"),
    MEMBER("member", "用户"),
    QUESTION("question", "问答"),
    ANSWER("answer", "回答"),
    GROUPTOPIC("groupTopic", "群组话题"),
    CONTEST("contest", "大赛"),
    NEWS("news", "行业动态"),
    ASSEMBLY("assembly", "精选大会"),

    VISITOR("visitor","访问"),
    OPERATION("operation","操作"),
    //标签类型
    STRONG_TAG("<strong>", "</strong>"),
    SENSITIVE_TAG("<span style=\"background-color:yellow\">", "</span>"),
    ;

    String key;
    String desc;

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    EsType(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public static EsType getEsTypeByKey(String key){
        for(EsType esType : EsType.values()){
            if(StringUtils.equals(key, esType.getKey())){
                return esType;
            }
        }
        return null;
    }
}
