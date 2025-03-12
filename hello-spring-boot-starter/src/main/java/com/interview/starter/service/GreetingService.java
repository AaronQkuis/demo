package com.interview.starter.service;

/**
 * @author qikun
 * @date 3/12/2025  8:16 PM
 */
public class GreetingService {
    private final String prefix;
    private final String suffix;

    public GreetingService(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String greet(String name) {
        return prefix + " " + name + suffix;
    }
}
