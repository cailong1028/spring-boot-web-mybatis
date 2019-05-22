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


//事务，日志级别，改pool为两个hashmap的组合，locked，unlocked
//大量测试
public class PoolManager {

    Logger logger = LoggerFactory.getLogger(PoolManager.class);

    private static PoolManager instance = null;

    private Map<String, PoolConnection> connectionPool = new HashMap<>();

    private int maxSize = 2;
    private int mixSize = 2;
    private int timeout = 5000;
    private int maxFreeTime = 15000;
    private int scanPeriod = 3000;

    private volatile boolean monitorRunning = false;
    private volatile int monitorCnt = 0;

    private PoolManager(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            startMonitor();
        } catch (ClassNotFoundException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    private void startMonitor(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                logger.info("pool manager monitor running");
                while(true){
                    try {
                        synchronized (connectionPool) {
                            if (connectionPool.size() > 0) {
                                logger.info("monitoring now connectionPool.size = " + connectionPool.size());
                                long now = System.currentTimeMillis();
                                LinkedList<PoolConnection> freeList = new LinkedList<>();
                                for (String oneKey : connectionPool.keySet()) {
                                    PoolConnection poolConnection = connectionPool.get(oneKey);
                                    if (!poolConnection.working && now - poolConnection.getTime() > maxFreeTime) {
                                        freeList.push(poolConnection);
                                    }
                                }
                                logger.info("monitoring will remove freeList.size = " + freeList.size());
                                if (freeList.size() > 0) {
                                    removePoolConnection(freeList);
                                }
                            }
                        }
                        //一定不能放在synchronized快中，否则即便notify了，块内容没有执行完毕，connectionPool锁就不释放
                        Thread.currentThread().sleep(scanPeriod);
                    } catch (InterruptedException e) {
                        monitorRunning = false;
                        try {
                            Thread.currentThread().join();
                            monitorCnt ++;
                            startMonitor();
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        logger.error(e);
                        //e.printStackTrace();
                    }
                }
            }
        };
        //移除超时空闲
        Thread monitor = new Thread(runnable);
        //跟随主线程结束，不控制进程
        monitor.setDaemon(true);
        monitor.setName("prime PoolManager monitor-" + monitorCnt);

        monitor.start();
        //monitorRunning = true;
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

    public PoolConnection getPoolConnection() throws TimeoutException, SQLException {
        PoolConnection poolConn = null;
        long begin = System.currentTimeMillis();
        while(poolConn == null){
            if(System.currentTimeMillis() - begin > timeout){
                Map<String, Integer> state = state();
                logger.info("working %d free %d time out when get PoolConnection", state.get("working"), state.get("free"));
                throw new TimeoutException();
            }
            //getFreeConnection在同步内执行
            synchronized (connectionPool) {
                poolConn = getFreeConnection();
                if(poolConn != null){
                    break;
                }
                if (connectionPool.size() < maxSize) {
                    try {
                        poolConn = createConnection();
                        poolConn = createConnection();
                        connectionPool.put(poolConn.getId(), poolConn);
                    } catch (SQLException e) {
                        logger.error(e);
                        throw e;
                    }
                }
                if (poolConn != null) {
                    break;
                }
                try {
                    //尝试重新获取连接
                    logger.warn("i will wait");
                    connectionPool.wait(timeout);
                } catch (InterruptedException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
        }
        poolConn.setWorking(true);

        return poolConn;
    }

    private PoolConnection getFreeConnection(){
        if(connectionPool.keySet().size() < 1){
            return null;
        }
        Set<String> keys = connectionPool.keySet();
        for(String oneKey:keys){
            if(!connectionPool.get(oneKey).working){
                return connectionPool.get(oneKey);
            }
        }
        return null;
    }

    boolean releasePoolConnection(PoolConnection poolConnection){
        //workingPool.
        if(poolConnection != null){
            synchronized (connectionPool){
                poolConnection.setWorking(false);
                logger.info("release one %s", poolConnection.getId());
                connectionPool.notify();
                return true;
            }
        }
        return false;
    }

    void removePoolConnection(LinkedList<PoolConnection> list){
        synchronized (connectionPool){
            PoolConnection poolConnection = list.pop();
            while( poolConnection != null){
                Connection connection = poolConnection.getConnection();
                try {
                    connection.close();
                } catch (SQLException e) {
                    connection = null;
                    logger.error(e);
                    //e.printStackTrace();
                }finally {
                    connectionPool.remove(poolConnection.getId());
                }
            }
            connectionPool.notify();
        }
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

    public Map<String, Integer> state(){
        Map<String, Integer> state = new HashMap<>();
        int working = 0, free = 0;
        for(String oneKey:connectionPool.keySet()){
            if (connectionPool.get(oneKey).isWorking()) {
                working++;
            } else {
                free++;
            }
        }
        state.put("working", working);
        state.put("free", free);
        return state;
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

        public String getId(){
            return this.id;
        }

        public Connection getConnection(){
            return this.connection;
        }

        public long getTime(){
            return this.time;
        }

        public boolean isWorking() {
            return working;
        }

        public boolean setWorking(boolean b) {
            return this.working = b;
        }
    }
}


