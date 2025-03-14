package com.interview.web.constants;

public final class WebConsts {

    /**
     * 高亮显示前缀标签
      */
    public static final String HIGHLIGHT_PRE_TAG = "<span style=\"color:red\">";
    /**
     * 高亮显示后缀标签
     */
    public static final String HIGHLIGHT_POST_TAG = "</span>";

    /**
     * tocken认证key
     */
    public static final String X_USER_TOKEN_HEADER = "S-User-Token";
    public static final String APP_NAME = "cosmo-sns";

    /**
     * 已审核文章状态
     */
    public static final String AUDIT_STATUS = "1";

    /**
     * 请求参数字符串最大长度
     */
    public static final int DEFAULT_INPUT_MAX_LENGTH = 30;
    /**
     * websocket redis订阅
     */
    public static final String WEBSOCKET_REDIS_TOPIC="WEBSOCKET_REDIS_CHANNEL_SCAN";
}
