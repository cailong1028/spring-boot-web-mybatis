package com.modules.demo2.test.nio.netty.primary.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static final String QUEUE_NAME = "test-queue";
    public static final String EXCHANGE_FANOUT_NAME = "test-exchange-fanout";
    public static final String EXCHANGE_FANOUT_QUEUE_PREFIX = "test-exchange-fanout-queue-prefix-";
    public static final String EXCHANGE_DIRECT_NAME = "test-exchange-direct";
    public static final String EXCHANGE_DIRECT_QUEUE_PREFIX = "test-exchange-direct-queue-prefix-";
    public static final String EXCHANGE_DIRECT_ROUTING_KEY_1 = "exchange_direct_routing_key_1";
    public static final String EXCHANGE_DIRECT_ROUTING_KEY_2 = "exchange_direct_routing_key_2";

    public static void main(String[] args) throws IOException, TimeoutException {

        //注1：queueDeclare第一个参数表示队列名称、第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）、第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除）、第四个参数为当所有消费者客户端连接断开时是否自动删除队列、第五个参数为队列的其他参数
        //注2：basicPublish第一个参数为交换机名称、第二个参数为队列映射的路由key、第三个参数为消息的其他属性、第四个参数为发送信息的主体
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(Producer.QUEUE_NAME, false, false, false, null);
        Runnable runnable = () -> {
            int msg = 1;

            while(true){
                try {
                    channel.basicPublish("", Producer.QUEUE_NAME, null, ("hello rabbitmq " + msg++).getBytes("UTF-8"));
                    Thread.currentThread().sleep(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable runnable2 = () -> {
            long time = new Date().getTime();
            try {
                channel.basicPublish("", Producer.QUEUE_NAME, null, ("another " + time).getBytes("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Thread t1 = new Thread(runnable);
        t1.start();

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(runnable2, 1, 1, TimeUnit.SECONDS);

//        channel.close();
//        connection.close();

        //exchange fanout
        Channel channel3 = connection.createChannel();
        channel3.exchangeDeclare(Producer.EXCHANGE_FANOUT_NAME, BuiltinExchangeType.FANOUT, false, true, null);
        Runnable runnable3 = () -> {
            try {
                channel3.basicPublish(Producer.EXCHANGE_FANOUT_NAME, "", null, "exchange test".getBytes("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(runnable3, 1, 1, TimeUnit.SECONDS);

        //exchange routing
        channel3.exchangeDeclare(Producer.EXCHANGE_DIRECT_NAME, BuiltinExchangeType.DIRECT);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                channel3.basicPublish(Producer.EXCHANGE_DIRECT_NAME, Producer.EXCHANGE_DIRECT_ROUTING_KEY_1, null, "routing key 1".getBytes("UTF-8"));
                channel3.basicPublish(Producer.EXCHANGE_DIRECT_NAME, Producer.EXCHANGE_DIRECT_ROUTING_KEY_2, null, "routing key 2".getBytes("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 1, 1, TimeUnit.SECONDS);

        //RPC remote procedure call

    }
}
