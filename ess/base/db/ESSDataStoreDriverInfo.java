/******************************************************************************
    File: ESSDataStoreDriverInfo.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/

package ess.base.db;

import java.util.*;
import javax.sql.DataSource;

import conf.PropertyConfigParser;

/**
 * 数据库连接信息的保存对象，用于存放多个数据库连接信息，用于系统
 * 访问多个数据库。数据库连接信息对象信息一般保存在配置文件中，系
 * 统初始化时生成此对象，供访问数据库时使用。
 * 数据库参数信息由db.properties提供，且通过conf/PropertyConfigParser
 * 类获取参数，使用本基础类时，必须保证本类能够访问到类
 * conf.PropertyConfigParser
 *
 * @see ESSDataStoreManger
 * @see ESSDataStore
 */
class ESSDataStoreDriverInfo{
    /**
     * 数据库配置文件
     */
    private final String DB_CONFIG_FILE = "db.properties";

    /**
     * Used to keep track of ESSDataStore instances.
     */
    private Hashtable dataStores = new Hashtable();

    /**
     * The prefered data store.
     */
    private String preferredDBName = null;
   
    /**
     * Creates datastore driver info instance.
     */
    public ESSDataStoreDriverInfo() throws Exception{
        parseDataSources();
    }

    public Hashtable getDataSources(){
        return dataStores;
    }

    public String getPreferredDBName() {
        return preferredDBName;
    }

    /**
     * The Contructor calls this method to initial the object.
     */
    private void parseDataSources() throws Exception{

        // Convert db.properties to an instance of Properties
        Properties dbProps = PropertyConfigParser.loadConfigFile(DB_CONFIG_FILE);

        // Get the preferred datastore
        this.preferredDBName = dbProps.getProperty("defaultdb");
        loadDrivers(dbProps);

        createDataSources(dbProps);
    }

    /**
     * Loads and registers all JDBC drivers specified by the drivers property.
     */
    private void loadDrivers(Properties props) throws ClassNotFoundException{
        String driverClasses = props.getProperty("drivers");
        StringTokenizer st = new StringTokenizer(driverClasses);
        while(st.hasMoreElements()){
            String driverClassName = st.nextToken().trim();
            Class.forName(driverClassName);
        }
    }

    /**
     * Creates all DataStores to the specified DB URLs.
     */
    private void createDataSources(Properties props) throws Exception{
        DataSource ds = null;
        /*
         * Defines the parameter used to identify a DB.
         */
        String dataSourceName = null;
        String url = null;
        boolean pooled=false;
        String user=null;
        String password=null;
        int max = -1;
        int init = -1;
        int timeOut = -1;
        long objExpireTime = -1;
        String checkTableName=null;
        int logLevel = -1;
        
        /*
         * Parses the DB configuration one by one.
         */
        Enumeration propNames = props.propertyNames();
        while(propNames.hasMoreElements()) {
            // the first property must be 'url'.
            String name = (String)propNames.nextElement();
            if(!name.endsWith(".url")){
                continue;
            }
            /*
             * 如果找到一个以.url结尾的参数，则说明该参数为数据源配置参数，
             * 将所有的与该url相关的属性按照顺序处理，最后生成一个datastore.
             */
            dataSourceName = name.substring(0, name.lastIndexOf("."));
            url = props.getProperty(dataSourceName+".url");
            // Check url, it cann't be null.
            if(url==null) {
                continue;
            }

            String pooledString = props.getProperty(dataSourceName+".pooled");
            pooled = pooledString.equalsIgnoreCase("true") ? true:false;
            user = props.getProperty(dataSourceName+".user");
            password = props.getProperty(dataSourceName+".password");

            if(url.startsWith("jdbc/")){
                try{
                    ds = new WSDataSource(url,user,password);
                }catch(Exception ex){
                    ds = null;
                }
            }else if(pooled==true) {
                String maxConns = props.getProperty(dataSourceName+".maxconns","10");
                // Check max, it must >=0;
                max=Integer.valueOf(maxConns).intValue();
                String initConns=props.getProperty(dataSourceName+".initconns","0");
                init = Integer.valueOf(initConns).intValue();
                String getTimeout = props.getProperty(dataSourceName+".timeout","5");
                timeOut = Integer.valueOf(getTimeout).intValue()*1000;
                String expireTimeout = props.getProperty(dataSourceName+".expiretime","30");
                objExpireTime = Integer.valueOf(expireTimeout).intValue()*1000;
                checkTableName = props.getProperty(dataSourceName+".checktable");
                if(checkTableName != null && checkTableName.trim().length() == 0){
                    checkTableName = null;
                }

                try{
                    ds = new PooledDataSource(null,url,user, password, objExpireTime, timeOut, max, init, checkTableName);
                }catch(Exception ex){
                    ds = null;
                }
            }else{
                ds = new NoPoolDataSource(url,user,password);
            }
            if(ds != null){
                dataStores.put(dataSourceName, ds);
            }
        }
    }
}
