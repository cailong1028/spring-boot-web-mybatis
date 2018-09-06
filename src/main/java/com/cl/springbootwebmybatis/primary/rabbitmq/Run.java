package com.cl.springbootwebmybatis.primary.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Run {

    public static void main(String[] args) throws IOException, TimeoutException {
        new RPCClient().call("sdfsdf");
    }

}
