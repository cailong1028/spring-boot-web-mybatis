/******************************************************************************
    File: ESSDataStoreManager.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/
package ess.base.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import ess.base.exp.*;

/**
 * 数据库连接信息管理类。通过数据库连接信息的应用别名，通过此类
 * 获取数据库连接和对连接进行维护，是系统访问数据库的接口应用类。
 *
 * @see ess.base.db.ESSDataStoreDriverInfo
 */
public class ESSDataStoreManager{
    /**
     * 数据库错误日志文件
     */
    private String logFile = "db";  

    private ess.base.util.LogFile logWriter = null;

    /**
     * Used to hold the reference to a single instance of PoolManager.
     */
    private static ESSDataStoreManager instance=null;

    /**
     * Used to keep track of ESSDataStore instances.
     */
    private Hashtable dataSources = new Hashtable();

    /**
     * The default database for accessing
     */
    private String preferredDBName = null;
    
    /**
     * Contructor. it is defined as private to prevent orther objects from creating
     * instances of the class.
     */
    private ESSDataStoreManager(){
        try{
            init();
        }catch(Throwable ex){
            logWriter.log("Access data source config file error", ex);
        }
    }

    /**
     * Clients of the PoolManager call this method to get a reference to the 
     * single instance.
     */
    public static synchronized ESSDataStoreManager getInstance(){
        if(instance==null) {
            instance = new ESSDataStoreManager();
        }
        return instance;
    }

    /**
     * Releases all resources used by the PoolManager.
     */
    public synchronized void release(){
        instance = null;
    }

    /**
     * Get the specified data store object
     * @param dataStoreName the data store name
     * @return ESSDataStore the specified data store
     */
    public ESSDataSource getDataStore(String dataStoreName){
        if(dataSources != null){
            return (ESSDataSource)dataSources.get(dataStoreName); 
        }else{
            return null;
        }
    }

    /**
     * Returns the connection to the client preferred DB .
     */
    public Connection getConnection()
    throws SQLException, ESSConnectionGetTimeOutException{
        return getConnection(preferredDBName);
    }

    /**
     * Gets the connection from the named data store.
     * @param name The name of the data store
     * @return the instance of named data store
     */
    public Connection getConnection(String name)
    throws SQLException, ESSConnectionGetTimeOutException{
        ESSDataSource ds= (ESSDataSource)dataSources.get(name);
        Connection conn = null;
        
        if(ds!=null){         	
            conn=ds.getConnection();
        }
      
        return conn;
    }

    public void freeConnection(Connection conn)throws SQLException{
        this.freeConnection(conn, preferredDBName);
    }    

    /**
     * Returns the connection,if it is a connection pool, then return to 
     * its original pool,else close it.
     * @param con the connection to free
     * @param name The name of DataStore
     */
    public void freeConnection(Connection con,String dataStoreName)throws SQLException{
        ESSDataSource ds = (ESSDataSource)dataSources.get(dataStoreName);
        if(ds!=null){
            ds.freeConnection(con);
            //notifyAll();
        }
    }

    public void setPreferredDBName(String preferredDBName){
        this.preferredDBName = preferredDBName;        
    }

    /**
     * 获取日志记录器
     * @return ess.base.util.LogFile
     */
    public ess.base.util.LogFile getLogWriter(){
        return logWriter;
    }

    /**
     * 监视当前的数据库连接情况
     */
    public String monitorConnection(String name) {
        return ((ESSDataSource)dataSources.get(name)).monitor();
    }

    /**
     * The Contructor calls this method to initial the object.
     */
    private void init() throws Exception{
        // Construct log writer.
        logWriter = new ess.base.util.LogFile(logFile, true);

        // Get the datasource information
        ESSDataStoreDriverInfo dsInfo = new ESSDataStoreDriverInfo();
        this.preferredDBName = dsInfo.getPreferredDBName();
        this.dataSources = dsInfo.getDataSources();
        dsInfo = null;
    }
}
