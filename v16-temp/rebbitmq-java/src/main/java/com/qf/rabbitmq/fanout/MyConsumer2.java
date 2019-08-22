package com.qf.rabbitmq.fanout;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author xiaoxinmin
 * @Date 2019/8/12
 * 消费者用于接收消息进行处理
 */
public class MyConsumer2 {

    private static final String EXCHANGR_NAME="fanout-exchange";
    private static final String QUEUE_NAME="fanout-exchange-queue2";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("47.103.21.241");
        factory.setUsername("xxm");
        factory.setPassword("123");
        factory.setPort(5672);
        factory.setVirtualHost("/xxm");
        Connection connection = factory.newConnection();
        //创建消费者的通道
        final Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //绑定队列和交换机
        channel.queueBind(QUEUE_NAME, EXCHANGR_NAME, "");
        //设置消费者每次处理的消息个数
        channel.basicQos(1);
        //消费者处理消息
        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //super.handleDelivery(consumerTag, envelope, properties, body);
                String message=new String(body,"utf-8");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者2接收到的信息为"+message);
                //回复一个消息给mq服务器
                //channel.basicAck(envelope.getDeliveryTag(), false);
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //消费者监听队列
        //设置回复模式为手动
        channel.basicConsume(QUEUE_NAME, false, consumer);

    }
}
