package com.example.account_service.config.async;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ForkJoinPool;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Value("${async.pools.worker}")
    private String prefix;

    @Bean(name = "workerAsync")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix(prefix);
        executor.initialize();
        return executor;
    }

    @Bean(name = "forkJoinPool")
    public ForkJoinPool forkJoinPool() {
        return new ForkJoinPool();
    }
}
