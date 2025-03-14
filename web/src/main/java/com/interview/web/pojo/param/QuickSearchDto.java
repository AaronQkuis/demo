package com.interview.web.pojo.param;

import lombok.Data;

@Data
public class QuickSearchDto {

    /**
     * id
     */
    private Integer id;

    /**
     * 结果类型
     */
    private String type;

    /**
     * 结果标题
     */
    private String title;

    /**
     * 话题对应的群组id
     */
    private Integer groupId;
}
