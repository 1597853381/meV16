package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoxinmin
 * @Date 2019/8/12
 */
@Configuration
public class CommonConfig {

    @Bean
    public ThreadPoolExecutor initThreadPoolExecutor(){
        Runtime runtime = Runtime.getRuntime();
        int processors = runtime.availableProcessors();
        ThreadPoolExecutor pool=new ThreadPoolExecutor(
                processors, processors * 2, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100)) ;
        return pool;
    }
}
