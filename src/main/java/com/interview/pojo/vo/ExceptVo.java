package com.interview.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zxc
 */
@Data
@ApiModel
public class ExceptVo {

    @ApiModelProperty(value = "异常码")
    private Integer code;

    @ApiModelProperty(value = "异常信息")
    private String message;

    public ExceptVo() {
    }

    public ExceptVo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
