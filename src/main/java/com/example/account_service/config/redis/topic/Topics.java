package com.example.account_service.config.redis.topic;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "topic.redis")
public record Topics(
        String channelInfoAccount,
        String channelErrorAccount
) {
}
