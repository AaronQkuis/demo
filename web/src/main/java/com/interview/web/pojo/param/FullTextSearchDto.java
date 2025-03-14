package com.interview.web.pojo.param;

import lombok.Data;

/**
 * @author haoxuexin
 */
@Data
public class FullTextSearchDto {

    private String content;

    private Integer pageNum;

    private Integer pageSize;

    private String type;

}
