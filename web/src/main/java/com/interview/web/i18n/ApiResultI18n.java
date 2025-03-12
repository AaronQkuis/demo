package com.interview.web.i18n;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.IOException;
import java.io.Serializable;

/**
 * @Description: 多语言, 国际化接口返回结果封装
 * @Author: mamy
 * @Date: 2020/2/27
 */
@Data
@ApiModel
public class ApiResultI18n<T> implements Serializable {

    private static final long serialVersionUID = 4518290031778225230L;

    private static int TO_URL = 2;
    private static int REFRESH_IEFRAME = 3;

    /**
     * 返回码，-1操作失败，0操作成功 -2参数错误 2成功并跳转到url，3成功并刷新iframe的父界面
     */
    @ApiModelProperty(value = "返回码，-1操作失败，0操作成功 -2参数错误 2成功并跳转到url，3成功并刷新iframe的父界面")
    private int code = 0;

    /**
     * 返回信息
     */
    @ApiModelProperty(value = "返回信息")
    private String message = "成功";
    /**
     * 跳转url
     */
    @ApiModelProperty(value = "跳转url")
    private String url = null;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    private T data;

    /**
     * api 返回结果
     */
    private ApiResultI18n() {}

    /**
     * api 返回结果,区分多语言
     *
     * @param language 语言类型,eg: en_us 表示美式英文
     */
    public ApiResultI18n(String language){
        this.code = ResponseCodeI18n.SUCCESS.getCode();
        language = LanguageEnum.getLanguageType(language);
        try {
            this.message = I18nMessageUtil.getMessage(language,ResponseCodeI18n.SUCCESS.getMsg(),"SUCCESS");
        } catch (IOException e) {
            this.message = "SUCCESS";
        }
    }

    /**
     * 获取成功状态结果,区分多语言(默认简体中文)
     *
     * @param language 语言类型,eg: en_us 表示美式英文
     * @return
     */
    public static ApiResultI18n success(String language) {
        return success(null, language);
    }

    /**
     * 获取成功状态结果,区分多语言(默认简体中文)
     *
     * @param data 返回数据
     * @param language 语言类型,eg: en_us 表示美式英文
     * @return
     */
    public static<T> ApiResultI18n success(T data, String language) {
        ApiResultI18n result = new ApiResultI18n(language);
        result.setData(data);
        return result;
    }
    public static<T> ApiResultI18n success(T data) {
        ApiResultI18n result = new ApiResultI18n("zh_CN");
        result.setData(data);
        return result;
    }
    public static<T> ApiResultI18n<T> successWithTypeCheck(T data) {
        ApiResultI18n<T> result = new ApiResultI18n<>("zh_CN");
        result.setData(data);
        return result;
    }
    /**
     * 获取成功状态结果,区分多语言(默认简体中文)
     *
     * @param code code
     * @param url 跳转url
     * @param language 语言类型,eg: en_us 表示美式英文
     * @return
     */
    public static<T> ApiResultI18n success(int code, String url, String language) {
        ApiResultI18n result = new ApiResultI18n(language);
        result.setUrl(url);
        result.setCode(code);
        return result;
    }

    /**
     * 获取失败状态结果,区分多语言(默认简体中文)
     *
     * @param language 语言类型,eg: en_us 表示美式英文
     * @return
     */
    public static ApiResultI18n failure(String language) {
        return failure(ResponseCodeI18n.FAIL.getCode(), ResponseCodeI18n.FAIL.getMsg(), null, language);
    }

    /**
     * 获取失败结果,区分多语言(默认中文)
     *
     * @param responseCodeI18n 返回码
     * @param language 语言类型
     * @return
     */
    public static ApiResultI18n failure(ResponseCodeI18n responseCodeI18n, String language) {
        return failure(responseCodeI18n.getCode(), responseCodeI18n.getMsg(), null, language);
    }

    public static ApiResultI18n failure(ResponseCodeI18n responseCodeI18n) {
        return failure(responseCodeI18n.getCode(), responseCodeI18n.getMsg(), null, "zh_CN");
    }

    /**
     * 获取失败状态结果,区分多语言(默认中文)
     *
     * @param code 返回状态码
     * @param msg 错误信息
     * @param language 语言类型,eg: en 表示英文
     * @return
     */
    public static ApiResultI18n failure(int code, String msg, String language) {
        return failure(code ,msg, null, language);
    }
    public static ApiResultI18n failure(int code, String msg) {
        return failure(code ,msg, null, "zh_CN");
    }
    /**
     * 获取失败返回结果,区分多语言(默认中文)
     *
     * @param code 错误码
     * @param msg 错误信息
     * @param data 返回结果
     * @param language 语言类型,eg: en 表示英文
     * @return
     */
    public static<T> ApiResultI18n failure(int code, String msg, T data, String language) {
        ApiResultI18n result = new ApiResultI18n(language);
        language = LanguageEnum.getLanguageType(language);
        try {
            msg = I18nMessageUtil.getMessage(language, msg, msg);
        } catch (IOException e) {
            msg = "Error";
        }
        result.setCode(code);
        result.setMessage(msg);
        result.setData(data);
        if (data instanceof String) {
            String m = (String) data;
            if (!m.matches("^.*error$")) {
                m += "error";
            }
        }
        return result;
    }

    public static <T> ApiResultI18n<T> ok(T data) {
        ApiResultI18n<T> result = new ApiResultI18n<T>("zh_CN");
        result.setData(data);
        return result;
    }
}
