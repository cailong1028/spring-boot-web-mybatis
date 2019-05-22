package com.modules.prime.sql.mysql;

import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.concurrent.TimeoutException;

public class BO {

    Logger logger = LoggerFactory.getLogger(BO.class);

    public JSONArray query(String sql, Object... args){
        PoolManager poolManager = PoolManager.getInstance();
        PoolManager.PoolConnection poolConnection = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        JSONArray ja = new JSONArray();
        try {
            poolConnection = poolManager.getPoolConnection();
            logger.info(poolConnection.getId());
            conn = poolConnection.getConnection();
            pst = conn.prepareStatement(sql);
            for(int k = 0; k < args.length; k++){
                pst.setObject(k + 1, args[k]);
            }
            rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int colCnt = rsmd.getColumnCount();
            while(rs.next()){
                JSONObject jo = new JSONObject();
                for(int i = 1; i <= colCnt; i++){
                    jo.put(rsmd.getColumnName(i), rs.getObject(rsmd.getColumnName(i)));
                }
                ja.put(jo);
            }
//            Map<String, Integer> state = poolManager.state();
//            logger.info("working %d free %d", state.get("working"), state.get("free"));
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
            if(pst != null){
                try {
                    pst.close();
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
            if(poolConnection != null){
                poolManager.releasePoolConnection(poolConnection);
            }
        }
        return ja;
    }

    public void batchAdd(){

    }

    public static void main(String[] args) {
        //LoggerFactory.setWriter("/Users/bqj/Desktop/a.txt");
        Logger logger = LoggerFactory.getLogger(BO.class);
        BO bo = new BO();
//        for(int i = 0; i < 2; i++){
//            final int b = i;
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    JSONArray ja = bo.query("select * from test where name = ?", "cl2");
//                    if(ja.length() > 0){
//                        logger.info(((JSONObject)ja.get(0)).getString("name"));
//                    }
//                }
//            }).start();
//        }
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for(int i = 0; i < 100; i++){
            final int b = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONArray ja = bo.query("select * from test where name = ?", "cl");
                    if(ja.length() > 0){
                        logger.info(((JSONObject)ja.get(0)).getString("name"));
                    }
                }
            }).start();
        }
    }
}
