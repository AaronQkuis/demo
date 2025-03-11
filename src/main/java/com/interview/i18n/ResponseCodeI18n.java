package com.interview.i18n;

import lombok.Getter;
import lombok.ToString;

/**
 * @Description: 多语言,国际化接口返回码枚举
 * @Author: mamy
 * @Date: 2020/2/27
 */
@Getter
@ToString
public enum  ResponseCodeI18n {

    SUCCESS(0, "api.response.code.success"),
    FAIL(-1, "api.response.code.fail"),

    // 公共参数
    PARAM_ERROR(-2, "api.response.code.paramError"),
    LANGUAGE_TYPE_ERROR(1002, "api.response.code.languageTypeError"),
    UNKNOWN_ERROR(-1000,"api.response.code.unknownError"),

    ;

    // 返回码
    private int code;

    // 返回信息
    private String msg;

    private ResponseCodeI18n(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}