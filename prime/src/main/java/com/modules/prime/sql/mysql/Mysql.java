package com.modules.prime.sql.mysql;


import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;

import java.sql.*;
import java.util.*;

public class Mysql {
    Logger logger = LoggerFactory.getLogger(Mysql.class);

    private void test(){
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println(Mysql.class.getClassLoader());
        //System.out.println(classLoader.);
        //获取加在的类

        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://119.29.108.40:3306/db_test?characterEncoding=utf8", "cl", "cl");

            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            statement = conn.createStatement();
            String sql = "select * from test limit 2;";
            //statement.execute(sql);
            rs = statement.executeQuery(sql);
            rs.last();
            logger.info("cont %d", rs.getRow());
            rs.beforeFirst();
            List<Map<String, Object>> list = new ArrayList<>();
            ResultSetMetaData rsmd = rs.getMetaData();
            int colCnt = rsmd.getColumnCount();
            while(rs.next()){
                Map<String, Object> oneMap = new HashMap<>();
                for(int i = 1; i <= colCnt; i++){
                    oneMap.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                list.add(oneMap);
            }
            for(Map one:list){
                Set<String> keys = one.keySet();
                StringBuffer out = new StringBuffer();
                for(String oneKey: keys){
                    out.append(one.get(oneKey)+"\t");
                }
                logger.info(out.toString());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
        new Mysql().test();
    }
}
