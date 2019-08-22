package com.qf.rebbitmqspringboot.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoxinmin
 * @Date 2019/8/13
 */
@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue initQueue(){
        return new Queue("springboot-queue", false, false, false);
    }
}
