package com.interview.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author qikun
 * @date 3/12/2025  8:15 PM
 */
@ConfigurationProperties(prefix = "greeting")
public class GreetingProperties {

    /**
     * 问候语前缀
     */
    private String prefix = "Hello";

    /**
     * 问候语后缀
     */
    private String suffix = "!";

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}