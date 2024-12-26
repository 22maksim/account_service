package com.example.account_service.config.redis;

import com.example.account_service.config.redis.listener.MessageListenerInfo;
import com.example.account_service.config.redis.properties.RedisProperties;
import com.example.account_service.config.redis.topic.Topics;
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

@RequiredArgsConstructor
@Configuration
public class RedisConfig {
    private final RedisProperties redisProperties;
    private final Topics topics;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(
                redisProperties.host(), redisProperties.port()
        );
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    RedisMessageListenerContainer container(JedisConnectionFactory jedisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);
        container.setTopicSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        container.addMessageListener(messageListenerAdapterInfo(), topicInfo());
        container.afterPropertiesSet();

        return container;
    }

    MessageListenerAdapter messageListenerAdapterInfo() {
        return new MessageListenerAdapter(
                new MessageListenerInfo()
        );
    }

    ChannelTopic topicInfo() {
        return new ChannelTopic(topics.channelInfoAccount());
    }
}
