/******************************************************************************
    File: ESSNonePooledDataStore.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/
package ess.base.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接类的实现类，用于实现不带连接池的数据库访问信息管理。
 *
 * @see ESSDataSource
 */
public class NoPoolDataSource implements ESSDataSource{
    private static int connNums = 0;

    /**
     * The database URL as "jdbc:<subprotocol>:<dbname>".
     */
    private String URL=null;

    /**
     * The user name of database account.
     */
    private String user=null;

    /**
     * The password of the database account.
     */
    private String password=null;

    public NoPoolDataSource(String URL, String user, String password){
        this.URL = URL;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException{
        Connection conn = null;
        try{
            if(user == null)
                conn = DriverManager.getConnection(URL);
            else
                conn = DriverManager.getConnection(URL, user, password);
        }catch(SQLException ex){
            //log("创建连接错误(非连接池)");
            throw ex;
        }

        connNums++;
        return conn;
    }

    public Connection getConnection(String username, String password) throws SQLException{
        return getConnection();
    }

    public void freeConnection(Connection conn) throws SQLException{
        // just close it
        if(conn != null){
            conn.close();
        }
        connNums--;
    }

    /**
     * 监视当前的数据库连接情况
     */
    public String monitor() {
        return "Connection Manager:" + URL + connNums + " connections";
    }

    public void setLoginTimeout(int seconds) throws SQLException{
        DriverManager.setLoginTimeout(seconds);
    }

    public int getLoginTimeout(){
        return DriverManager.getLoginTimeout();
    }
    public PrintWriter getLogWriter() throws SQLException{
        return DriverManager.getLogWriter();
    }

    public void setLogWriter(PrintWriter out) throws SQLException{
        DriverManager.setLogWriter(out);
    }
}
