package com.suxin.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @Classname RateLimiterConfig
 * @Description [ 限流配置类，根据url路径 ]
 * @Author Tang
 * @Date 2020/10/28 11:37
 * @Created by ASUS
 */
@Configuration
public class RateLimiterConfig {

//    @Bean(value = "apiKeyResolver")
    public KeyResolver apiKeyResolver() {
        // 超出限流的部分状态码返回 429 Too Many Requests
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }

}