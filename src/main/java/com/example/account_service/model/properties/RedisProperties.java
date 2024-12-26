package com.example.account_service.model.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.data.redis")
public record RedisProperties (
        String host,
        int port
) {
}
