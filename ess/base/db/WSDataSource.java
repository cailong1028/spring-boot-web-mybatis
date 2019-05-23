/******************************************************************************
    File: WSPooledDataStore.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/
package ess.base.db;

import java.util.Hashtable;
import java.io.PrintWriter;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

//import com.ibm.ejs.dbm.jdbcext.*;

/**
 * 此类封装实现了WebSphere Application Server的Connection Pooling
 * 管理
 */
public class WSDataSource implements ESSDataSource {
    /**
     * The datasource name as "jdbc/<dbsourcename>".
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

    private javax.sql.DataSource ds = null;

    /**
     * 构造WebSphere数据源，生成javax.sql.DataSource对象。
     * @param dataSourceName java.lang.String 数据源名称
     * @param user java.lang.String 访问数据源的用户名
     * @param password java.lang.String 访问数据源的用户密码
     */
    public WSDataSource(String dataSourceName, String user, String password) throws NamingException{
        this.URL = dataSourceName;
        this.user = user;
        this.password = password;

        init();
    }

    /**
     * Get a connection from the websphere data source pool
     */
    public java.sql.Connection getConnection() throws java.sql.SQLException {
        Connection conn = null;

        // Get a Connection object conn using the DataSource factory.
        if(ds != null){
            // WAS3.5 Model
            //conn = ds.getConnection(getUser(), getPassword());
            // WAS4.0 Model
            conn = ds.getConnection();
        }else{
            throw new java.sql.SQLException("Cann't get connection , the data source is null");
        }

        return conn;
    }

    /**
     * Get a connection from the websphere data source pool
     */
    public java.sql.Connection getConnection(String username, String password) throws java.sql.SQLException {
        // Get a Connection object conn using the DataSource factory.
        if(ds != null){
            return ds.getConnection(username, password);
        }else{
            throw new java.sql.SQLException("Cann't get connection , the data source is null");
        }
    }

    /**
     * Close the connection, which does not close actual connection, 
     * but releases it back to the pool。
     */
    public void freeConnection(java.sql.Connection conn) throws SQLException{
         if (conn != null){
            try{
                conn.close();
                conn = null;
            }catch (SQLException e){
               throw e;
            }
         }
    }

    /**
     * Get the datasorce object for db connection.
     */
    private void init() throws NamingException{
        //Create the initial naming context. 
        Hashtable parms = new Hashtable();
        // WAS3.5 Model
        //parms.put(Context.INITIAL_CONTEXT_FACTORY, 
              //"com.ibm.ejs.ns.jndi.CNInitialContextFactory");
        // WAS4.0 Model
        parms.put(Context.INITIAL_CONTEXT_FACTORY, 
              "com.ibm.websphere.naming.WsnInitialContextFactory");
        Context ctx = new InitialContext(parms);
        // Perform a naming service lookup to get a DataSource object.
        // The single DataSource object is a "factory" used by all
        // requests to get an individual connection for each request.
        // The Web administrator can provide the lookup parameters.
        // The code below uses a value read in from an external property
        // file.  The text string source is typically something like
        // "jdbc/sample" where "jdbc" is the context for the
        // lookup and "sample" is the logicalname of the DataSource
        // object to be retrieved.
        ds = (javax.sql.DataSource)ctx.lookup(URL);
    }

    /**
     * 监视当前的数据库连接情况
     */
    public String monitor() {
        return "WebSphere Connection Monitor";
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
