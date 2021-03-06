package com.mydreamplus.smartdevice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/17
 * Time: 上午12:14
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@EnableAsync
@EnableElasticsearchRepositories(basePackages = "org/springframework/data/elasticsearch/repositories")
public class SpringConfig {
//
//    @Bean
//    public ElasticsearchOperations elasticsearchTemplate() {
////        return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
//        return new ElasticsearchTemplate(new Bui);
//    }


    /**
     * 线程池参数配
     */
    private final int CORE_POOL_SIZE = 10;
    private final int MAX_POOL_SIZE = 200;
    private final int QUEUE_CAPACITY = 10;


    private Executor initExecutor(String namePrefix) {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix(namePrefix);
        executor.initialize();
        return executor;
    }

    /**
     * Log executor executor.
     *
     * @return the executor
     */
    @Bean
    public Executor logExecutor() {
        return initExecutor("LOG-");
    }

    /**
     * Message executor executor.
     *
     * @return the executor
     */
    @Bean
    public Executor messageExecutor() {
        return initExecutor("MESSAGE-");
    }


}
