package com.example.account_service.config.redis;

import com.example.account_service.config.redis.listener.MessageListenerInfo;
import com.example.account_service.config.redis.topic.RedisTopics;
import com.example.account_service.model.properties.RedisProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@RequiredArgsConstructor
@Configuration
public class RedisConfig {
    private final RedisProperties redisProperties;
    private final RedisTopics redisTopics;

    @Bean
    JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(
                redisProperties.host(), redisProperties.port()
        );
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    RedisMessageListenerContainer containerRedis(JedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.setTopicSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        container.addMessageListener(messageListenerAdapterInfo(), topicInfo());
        return container;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapterInfo() {
        return new MessageListenerAdapter(new MessageListenerInfo());
    }

    @Bean
    ChannelTopic topicInfo() {
        return new ChannelTopic(redisTopics.channelInfoAccount());
    }
}
