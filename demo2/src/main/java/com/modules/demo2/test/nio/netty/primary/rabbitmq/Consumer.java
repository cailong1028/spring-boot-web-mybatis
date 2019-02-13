package com.modules.demo2.test.nio.netty.primary.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {

        new Consumer();

    }

    public Consumer(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(this.oneConsumer(1));
        executorService.execute(this.oneConsumer(2));
        executorService.execute(this.exchangeConsumer);
        executorService.execute(() -> {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            try {
                Connection conn = factory.newConnection();
                Channel channel = conn.createChannel();
                channel.basicQos(1);
                channel.exchangeDeclare(Producer.EXCHANGE_DIRECT_NAME, BuiltinExchangeType.DIRECT);
                String queueName = channel.queueDeclare(Producer.EXCHANGE_DIRECT_QUEUE_PREFIX + "1", false, false, false, null).getQueue();
                String queueName2 = channel.queueDeclare(Producer.EXCHANGE_DIRECT_QUEUE_PREFIX + "2", false, false, false, null).getQueue();
                channel.queueBind(queueName, Producer.EXCHANGE_DIRECT_NAME, Producer.EXCHANGE_DIRECT_ROUTING_KEY_1);
                channel.queueBind(queueName2, Producer.EXCHANGE_DIRECT_NAME, Producer.EXCHANGE_DIRECT_ROUTING_KEY_2);
                DefaultConsumer consumer1 = new DefaultConsumer(channel){
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        String msg = new String(body, "UTF-8");
                        System.out.println(" ---> exchange direct queue1 receive msg: " + msg);
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }

                };
                DefaultConsumer consumer2 = new DefaultConsumer(channel){
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                        String msg = new String(body, "UTF-8");
                        System.out.println(" ---> exchange direct queue2 receive msg: " + msg);
                    }
                };
                channel.basicConsume(queueName, false, consumer1);
                channel.basicConsume(queueName2, true, consumer2);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        });
    }

    public Runnable oneConsumer(int i) {
        return () -> {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            try {
                Connection conn = factory.newConnection();
                Channel channel = conn.createChannel();
                channel.basicQos(1);
                channel.queueDeclare(Producer.QUEUE_NAME, false, false, false, null);
                DefaultConsumer consumer = new DefaultConsumer(channel){
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                        String msg = new String(body, "UTF-8");
                        System.out.println(i + " ---> receive msg: " + msg);
                    }
                };
                channel.basicConsume(Producer.QUEUE_NAME, true, consumer);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        };
    }

    public Runnable exchangeConsumer = () -> {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection conn = factory.newConnection();
            Channel channel = conn.createChannel();
            channel.basicQos(1);
            channel.exchangeDeclare(Producer.EXCHANGE_FANOUT_NAME, "fanout", false, true, null);
            String queueName = channel.queueDeclare(Producer.EXCHANGE_FANOUT_QUEUE_PREFIX + "1", false, false, false, null).getQueue();
            String queueName2 = channel.queueDeclare(Producer.EXCHANGE_FANOUT_QUEUE_PREFIX + "2", false, false, false, null).getQueue();
            channel.queueBind(queueName, Producer.EXCHANGE_FANOUT_NAME, "");
            channel.queueBind(queueName2, Producer.EXCHANGE_FANOUT_NAME, "");
            DefaultConsumer consumer1 = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                    String msg = new String(body, "UTF-8");
                    System.out.println(" ---> exchange fanout queue1 receive msg: " + msg);
                }
            };
            DefaultConsumer consumer2 = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                    String msg = new String(body, "UTF-8");
                    System.out.println(" ---> exchange fanout queue2 receive msg: " + msg);
                }
            };
            channel.basicConsume(queueName, true, consumer1);
            channel.basicConsume(queueName2, true, consumer2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    };

}
