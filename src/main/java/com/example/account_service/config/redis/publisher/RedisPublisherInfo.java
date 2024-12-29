package com.example.account_service.config.redis.publisher;

import com.example.account_service.config.redis.topic.RedisTopics;
import com.example.account_service.model.MessageFromPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@RequiredArgsConstructor
public class RedisPublisherInfo {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTopics redisTopics;
    private final ObjectMapper objectMapper;

    public void publisherInfo(Object info) throws JsonProcessingException {
        byte[] channelBytes = objectMapper.writeValueAsBytes(redisTopics.channelInfoAccount());
        byte[] infoBytes = objectMapper.writeValueAsBytes(info);
        MessageFromPublisher message = new MessageFromPublisher(channelBytes, infoBytes);

        redisTemplate.convertAndSend(redisTopics.channelInfoAccount(), message);
    }
}
