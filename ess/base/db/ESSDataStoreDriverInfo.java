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
 * ���ݿ�������Ϣ�ı���������ڴ�Ŷ�����ݿ�������Ϣ������ϵͳ
 * ���ʶ�����ݿ⡣���ݿ�������Ϣ������Ϣһ�㱣���������ļ��У�ϵ
 * ͳ��ʼ��ʱ���ɴ˶��󣬹��������ݿ�ʱʹ�á�
 * ���ݿ������Ϣ��db.properties�ṩ����ͨ��conf/PropertyConfigParser
 * ���ȡ������ʹ�ñ�������ʱ�����뱣֤�����ܹ����ʵ���
 * conf.PropertyConfigParser
 *
 * @see ESSDataStoreManger
 * @see ESSDataStore
 */
class ESSDataStoreDriverInfo{
    /**
     * ���ݿ������ļ�
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
             * ����ҵ�һ����.url��β�Ĳ�������˵���ò���Ϊ����Դ���ò�����
             * �����е����url��ص����԰���˳�����������һ��datastore.
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
