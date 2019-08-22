package com.qf.rebbitmqspringboot.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xiaoxinmin
 * @Date 2019/8/13
 */
@Component
@RabbitListener(queues = "springboot-queue")
public class Consumer {

    @RabbitHandler
    public void process(String message){
        System.out.println("接收到的消息是：—》"+message);
    }
}
