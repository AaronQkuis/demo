package com.interview.pojo.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author qikun
 * @date 3/11/2025  11:29 AM
 */
@Data
@ApiModel(description = "新建学生参数")
public class StudentParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("出生日期")
    private Date birthday;

    @ApiModelProperty("家庭住址")
    private String address;
}
