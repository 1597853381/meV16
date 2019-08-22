package com.qf.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoxinmin
 * @Date 2019/8/12
 * 生产者 用于发送消息
 */
public class Producer {

    private static final String QUEUE_NAME="work-queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("47.103.21.241");
        factory.setUsername("xxm");
        factory.setPassword("123");
        factory.setPort(5672);
        factory.setVirtualHost("/xxm");
        //工厂对象new连接
        Connection connection = factory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        for (int i = 0; i < 10; i++) {
            String message="java"+i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        }
        System.out.println("发送成功");
    }
}
