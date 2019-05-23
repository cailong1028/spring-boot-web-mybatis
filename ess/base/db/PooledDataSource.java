/******************************************************************************
    File: PooledDataSource.java

    Version 1.0
    Date            Author                Changes
    Jul.01,2003     Li Shengwang          Created
    Jun.10,2003     Li Shengwang          Updated for pool clear

    Copyright (c) 2003, Eagle Soar
    all rights reserved.
******************************************************************************/
package ess.base.db;

import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

import ess.base.util.ObjectPool;

/**
 * ����ʵ�������ݿ����ӳصĹ����ά��
 */
public class PooledDataSource extends ObjectPool implements ESSDataSource{
    /**
     * The database's JDBC name. format like "jdbc:<subprotocol>:<dbname>"
     */
    private String dburl = null;

    /**
     * The user id to access the database
     */
    private String user = null;

    /**
     * The password to access the database for the specified user id
     */
    private String password = null;

    /**
     * The table name for check the connetion valid
     */
    private String checkTable = null;

    /**
     * Construct the JDBC connection pool used the specified paramenter
     * @param driver The JDBC Driver name
     * @param url The database URL
     * @param usr The user id to access the specified database
     * @param pwd The user's password to access the specified database
     * @param checkTableName The table name for check the connection validation
     * @throws Exception When the initialize exception
     */
    public PooledDataSource(String driver, String url, String usr, String pwd, String checkTableName)
    throws Exception{
        super();
        init(driver,url,usr,pwd, checkTableName);
    }

    /**
     * ����JDBC���ݿ����ӳ�
     * @param driver JDBC���ݿ�Driver name
     * @param url ���ݿ�URL,such as jdbc:<subprotocol>:dbname
     * @param usr ���ݿ�����û���
     * @param pwd ���ݿ��û�����
     * @param objExpireTime ������ж����ʧЧǰ�Ŀ���ʱ��(milli seconds)
     * @param getTimeout ��ȡ�������ȴ�ʱ��(milli seconds)
     * @param maxObjNum ������еĶ���������
     * @param minObjNum ������б�������С����
     * @param checkTableName ��֤�����Ƿ���Ч�����ݱ�
     * @throws Exception ��������ش���ʱ�׳�
     */
    public PooledDataSource(String driver, String url, String usr, String pwd,
    long objExpireTime, long getTimeout, int maxObjNum, int minObjNum,
    String checkTableName) throws Exception{
        super(objExpireTime, getTimeout, maxObjNum, minObjNum);
        init(driver, url, usr, pwd, checkTableName);
    }

    /**
     * Get connection from the connection pool
     * @return Connection The getted connection
     * @throws SQLException Thrown when the database error
     */
    public Connection getConnection() throws SQLException {
        try {
            return ((Connection) super.checkOut());
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    /**
     * Get connection from the connection pool
     * @return Connection The getted connection
     * @throws SQLException Thrown when the database error
     */
    public Connection getConnection(String username, String password) throws SQLException {
        try {
            return ((Connection) super.checkOut());
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    /**
     * Free the connection to the connection pool
     * @param c
     */
    public void freeConnection(Connection conn) throws SQLException{
        super.checkIn(conn);
    }

    public String monitor(){
        return super.monitePool();
    }

    private void init(String driver, String url, String usr, String pwd,
    String checkTableName) throws Exception{
        if(driver != null){
            try {
                Class.forName(driver).newInstance();
            } catch (Exception e) {
                throw e;
            }
        }
        this.dburl = url;
        this.user = usr;
        this.password = pwd;
        this.checkTable = checkTableName;
    }

    protected Object create() throws SQLException {
        return DriverManager.getConnection(dburl, user, password);
    }

    /**
     * �������Ӷ����Ƿ���Ч
     * @see com.ess.pool.ObjectPool#validate(java.lang.Object)
     */
    protected boolean validate(Object obj) {
        try {
            if(checkTable != null && !checkTable.trim().equals("")){
                Connection conn = (Connection)obj;
                java.sql.Statement stmt = conn.createStatement();
                stmt.executeQuery("SELECT 1 FROM "+checkTable);
                stmt.close();
    
                return true;
            }else{
                return (!((Connection)obj).isClosed());
            }
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * �ͷ�ָ������
     * @see com.ess.pool.ObjectPool#expire(java.lang.Object)
     */
    protected void expire(Object obj) {
        try {
            ((Connection) obj).close();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
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
