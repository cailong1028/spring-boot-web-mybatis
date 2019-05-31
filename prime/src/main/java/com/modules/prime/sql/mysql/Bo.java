package com.modules.prime.sql.mysql;

import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class Bo {

    Logger logger = LoggerFactory.getLogger(Bo.class);

    public Bo(){

    }

    public List<Map<String, Object>> query(String sql, Object... args){
        PoolManager poolManager = PoolManager.getInstance();
        PoolManager.PoolConnection poolConnection = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Map<String, Object>> mapList = new ArrayList<>();
        try {
            poolConnection = poolManager.getPoolConnection();
            logger.debug("sql: [%s] ", sql);
            String[] argValues = new String[args.length];
            for(int i = 0; i < args.length; i++){
                argValues[i] = args[i].toString();
            }
            logger.debug("args: [%s] ", String.join(", ", argValues));
            conn = poolConnection.getConnection();
            pst = conn.prepareStatement(sql);
            for(int k = 0; k < args.length; k++){
                pst.setObject(k + 1, args[k]);
            }
            rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int colCnt = rsmd.getColumnCount();
            while(rs.next()){
                Map<String, Object> oneMap = new HashMap<>();
                for(int i = 1; i <= colCnt; i++){
                    oneMap.put(rsmd.getColumnName(i), rs.getObject(rsmd.getColumnName(i)));
                }
                mapList.add(oneMap);
            }
        } catch (TimeoutException e) {
            logger.error(e);
            //e.printStackTrace();
        } catch (SQLException e) {
            logger.error(e);
            //e.printStackTrace();
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
        return mapList;
    }

    public List<Map<String, Object>> transQuery(String sql, Object... args){
        PoolManager poolManager = PoolManager.getInstance();
        PoolManager.PoolConnection poolConnection = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Map<String, Object>> mapList = new ArrayList<>();
        try {
            poolConnection = poolManager.getPoolConnection();
            logger.debug("sql: [%s] ", sql);
            String[] argValues = new String[args.length];
            for(int i = 0; i < args.length; i++){
                argValues[i] = args[i].toString();
            }
            logger.debug("args: [%s] ", String.join(", ", argValues));
            conn = poolConnection.getConnection();
            pst = conn.prepareStatement(sql);
            for(int k = 0; k < args.length; k++){
                pst.setObject(k + 1, args[k]);
            }
            rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int colCnt = rsmd.getColumnCount();
            while(rs.next()){
                Map<String, Object> oneMap = new HashMap<>();
                for(int i = 1; i <= colCnt; i++){
                    oneMap.put(rsmd.getColumnName(i), rs.getObject(rsmd.getColumnName(i)));
                }
                mapList.add(oneMap);
            }
        } catch (TimeoutException e) {
            logger.error(e);
            //e.printStackTrace();
        } catch (SQLException e) {
            logger.error(e);
            //e.printStackTrace();
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
        return mapList;
    }

    public void batchAdd(){

    }

}
