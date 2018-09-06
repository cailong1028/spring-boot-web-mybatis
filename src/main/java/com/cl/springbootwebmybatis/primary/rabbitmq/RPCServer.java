package com.cl.springbootwebmybatis.primary.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

public class RPCServer {

    public static final String TEST_RPC_CHANNEL = "test_rpc_channel";

    public RPCServer(){

    }

    public void handleRequest() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.queueDeclare(RPCServer.TEST_RPC_CHANNEL, false, false, false, null);
        channel.basicQos(1);
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String dest = this.add(new String(body, "UTF-8"));
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties().builder().correlationId(properties.getCorrelationId()).build();
                channel.basicPublish("", properties.getReplyTo(), replyProps, dest.getBytes("UTF-8"));
            }

            public String add(String origin){
                return origin + "-abc";
            }
        };
        channel.basicConsume(RPCServer.TEST_RPC_CHANNEL, true, consumer);
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        new RPCServer().handleRequest();
    }

}
