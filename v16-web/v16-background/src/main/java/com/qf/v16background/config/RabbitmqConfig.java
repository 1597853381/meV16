package com.qf.v16background.config;

import com.qf.v16.common.base.constant.RabbitMQConstant;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author xiaoxinmin
 * @Date 2019/8/13
 */
@Component
public class RabbitmqConfig {

    @Bean
    public TopicExchange initTopicExchange(){
        return new TopicExchange(RabbitMQConstant.BACKGROUND_PRODUCT_EXCHANGE,true,false);
    }
}
