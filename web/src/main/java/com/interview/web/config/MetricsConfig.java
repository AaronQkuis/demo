package com.interview.web.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qikun
 * @date 3/15/2025  4:38 PM
 */
@Configuration
public class MetricsConfig {

    @Bean
    public Counter requestCounter(MeterRegistry registry) {
        return Counter.builder("app.requests.count")
                .description("Count of all requests")
                .register(registry);
    }
}
