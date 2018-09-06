package com.cl.springbootwebmybatis.primary.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class RPCClient {

    public static final String REPLY_TO_QUEUE = "reply_to_queue";

    ConnectionFactory factory;
    Connection conn;
    Channel channel;
    String replyTo;

    public RPCClient() throws IOException, TimeoutException {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        this.conn = factory.newConnection();
        this.channel = conn.createChannel();
        this.replyTo = channel.queueDeclare(RPCClient.REPLY_TO_QUEUE, false, false, false, null).getQueue();
        this.channel.basicQos(1);
        this.channel.queueDeclare(RPCServer.TEST_RPC_CHANNEL, false, false, false, null);
    }

    public RPCClient call(String arg) throws IOException {
        final String correlationId = UUID.randomUUID().toString();
        System.out.println(correlationId);

        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().replyTo(this.replyTo).correlationId(correlationId).build();
        this.channel.basicPublish("", RPCServer.TEST_RPC_CHANNEL, properties, arg.getBytes("UTF-8"));

        this.channel.basicConsume(this.replyTo, false, new DefaultConsumer(this.channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if(properties.getCorrelationId().equals(correlationId)){
                    System.out.println(correlationId + " PRC callback is --> " + new String(body, "UTF-8"));
                    getChannel().basicAck(envelope.getDeliveryTag(), false);
                }else{
                    getChannel().basicReject(envelope.getDeliveryTag(), true);
                    System.out.println(properties.getCorrelationId().toString() + " not equal " + correlationId);
                }
            }
        });

        return this;
    }

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            Executors.newCachedThreadPool().execute(() -> {
                try {
                    new RPCClient().call(Thread.currentThread().getName());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
