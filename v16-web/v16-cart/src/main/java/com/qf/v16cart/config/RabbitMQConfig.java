package com.qf.v16cart.config;

import com.qf.v16.common.base.constant.RabbitMQConstant;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author xiaoxinmin
 * @Date 2019/8/20
 */
@Component
public class RabbitMQConfig {

    @Bean
    public TopicExchange initTopicExchange(){
        return new TopicExchange(RabbitMQConstant.ADDORUPDATECART,true,false);
    }
}
