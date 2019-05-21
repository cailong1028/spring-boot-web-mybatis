package com.modules.prime.sql.mysql;

import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;
import com.modules.prime.test.BaseX;
import com.modules.prime.util.AppProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class PoolManager {

    Logger logger = LoggerFactory.getLogger(PoolManager.class);

    private static PoolManager instance = null;

    private Map<String, PoolConnection> connectionPool = new HashMap<>();

    private int maxSize = 10;
    private int mixSize = 2;
    private int timeout = 10000;

    private PoolManager(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    public static PoolManager getInstance(){
        if(instance != null){
            return instance;
        }
        synchronized (PoolManager.class){
            if(instance != null){
                return instance;
            }
            return instance = new PoolManager();
        }
    }

    public PoolConnection getConnection() throws TimeoutException, SQLException {
        PoolConnection poolConn = null;
        long begin = System.currentTimeMillis();
        synchronized (connectionPool){
            while(poolConn == null){
                if(System.currentTimeMillis() - begin > timeout){
                    logger.error("time out when getConnection");
                    throw new TimeoutException();
                }
                poolConn = freePool.poll();
                if(poolConn != null){
                    break;
                }
                if(freePool.size() + workingPool.size() < maxSize){
                    try {
                        poolConn = createConnection();
                    }catch (SQLException e){
                        logger.error(e);
                        throw e;
                    }

                    if(poolConn != null){
                        break;
                    }
                    try {
                        //尝试重新获取连接
                        wait(5000);
                    } catch (InterruptedException e) {
                        logger.error(e);
                        e.printStackTrace();
                    }
                }
            }
            workingPool.push(poolConn);
            return poolConn;
        }
    }

    private PoolConnection getFreeConnection(){
        if(connectionPool.keySet().size() < 1){
            return null;
        }
        Set<String> keys = new
        while()
    }

    synchronized boolean releaseConnection(PoolConnection poolConn){
        //workingPool.
        return true;
    }

    private PoolConnection createConnection() throws SQLException {
        //获取properties 加解密工具
        //AppProperties appProperties = new AppProperties();
        String url = BaseX.decode2str(AppProperties.get("mysql.url"));
        String name = BaseX.decode2str(AppProperties.get("mysql.username"));
        String password = BaseX.decode2str(AppProperties.get("mysql.password"));
        Connection conn = null;
        conn = DriverManager.getConnection(url, name, password);
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        return new PoolConnection(id, System.currentTimeMillis(), conn);
    }

    class PoolConnection{
        private String id;
        private long time;
        private Connection connection;
        private boolean working;
        private PoolConnection(String id, long time, Connection connection){
            this.id = id;
            this.time = time;
            this.working = true;
            this.connection = connection;
        }
    }
}


