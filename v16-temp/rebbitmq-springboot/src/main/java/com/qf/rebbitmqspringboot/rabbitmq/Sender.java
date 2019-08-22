package com.qf.rebbitmqspringboot.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaoxinmin
 * @Date 2019/8/13
 */
@Component
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String message){
        rabbitTemplate.convertAndSend("springboot-queue", message);
    }
}
