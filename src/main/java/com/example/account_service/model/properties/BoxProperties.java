package com.example.account_service.model.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "batch.box")
public record BoxProperties(
        int size
) {
}
