package com.modules.prime.sql.mysql;

import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class SBo {

    private int propagationDeep = 0;

    Logger logger = LoggerFactory.getLogger(SBo.class);
    PoolManager poolManager = PoolManager.getInstance();
    PoolManager.PoolConnection poolConnection = null;
    Connection conn = null;
    int isolation = Connection.TRANSACTION_REPEATABLE_READ;

    public SBo(){
        this(Connection.TRANSACTION_REPEATABLE_READ);
    }

    public SBo(int isolation){
        try {
            this.isolation = isolation;
            poolConnection = poolManager.getPoolConnection();
            poolConnection.setIsolation(this.isolation);
            poolConnection.setAutoCommit(false);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> query(String sql, Object... args){
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Map<String, Object>> mapList = new ArrayList<>();
        try {
            logger.debug("exec: [%s] ", sql);
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
        } catch (SQLException e) {
            logger.error(e);
            rollback();
            //e.printStackTrace();
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                logger.error(e);
                rollback();
                //e.printStackTrace();
            }
        }
        return mapList;
    }

    public void commit(){
        if(poolConnection != null){
            poolConnection.commit();
            poolManager.releasePoolConnection(poolConnection);
            poolConnection = null;
        }
    }

    public void rollback(){
        if(poolConnection != null){
            poolConnection.rollback();
            poolManager.releasePoolConnection(poolConnection);
            poolConnection = null;
        }
    }

    public void deepAdd(Class<?> invokerType, Method method){
        ++propagationDeep;
        logger.debug("connection [%s] ++deep, and deep now is [%d], method [%s]", this.poolConnection.getId(), propagationDeep, invokerType.getName()+":"+method.getName());
    }
    public void deepReduce(Class<?> invokerType, Method method){
        --propagationDeep;
        logger.debug("connection [%s] --deep, and deep now is [%d], method [%s]", this.poolConnection.getId(), propagationDeep, invokerType.getName()+":"+method.getName());
    }
    public int getDeep(){
        return propagationDeep;
    }

    public boolean isValid(){
        return poolConnection != null;
    }
}
