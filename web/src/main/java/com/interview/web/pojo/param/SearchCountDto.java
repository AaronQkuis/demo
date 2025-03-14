package com.interview.web.pojo.param;

import lombok.Data;

@Data
public class SearchCountDto {

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 群组话题数量
     */
    private Integer groupTopicCount;

    /**
     * 问答数量
     */
    private Integer qaCount;
}
