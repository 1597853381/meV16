package com.qf.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoxinmin
 * @Date 2019/8/12
 * 消费者用于接收消息进行处理
 */
public class MyConsumer {

    private static final String QUEUE_NAME="simple-queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("47.103.21.241");
        factory.setUsername("xxm");
        factory.setPassword("123");
        factory.setPort(5672);
        factory.setVirtualHost("/xxm");
        Connection connection = factory.newConnection();
        //创建消费者的通道
        Channel channel = connection.createChannel();
        //消费者处理消息
        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String message=new String(body,"utf-8");
                System.out.println("接收到的信息为"+message);
            }
        };
        //消费者监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }
}
