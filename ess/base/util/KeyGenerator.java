package ess.base.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import com.ess.util.DataFormat;

import ess.base.bo.ESSObject;

/**
 * 用于产生序列关键字的工具类
 */
public class KeyGenerator extends ESSObject{
	public static java.util.Hashtable keys = new Hashtable();
/**
 * KeyGenerator 构造子注解。
 */
public KeyGenerator() {
	super();
}
/**
 * 返回当前日期字符串，格式为YYYY-MM-DD
 */
public static String getCurrentDate() {
	return (new java.sql.Date(System.currentTimeMillis())).toString();
}
/**
 * 返回当前日期时间戳字符串，格式为YYYY-MM-DD HH:MM:SS.hms
 */
public static String getCurrentTimestamp() {
	return (new java.sql.Timestamp(System.currentTimeMillis())).toString();
}
	/**
	 * 返回指定条件的序列号
	 * pre: 序列号的前缀
	 * tableName: 指定的表名称
	 * keyName: 序列号的字段名
	 */
	public synchronized static String getSerial(String pre, String tableName, String keyName) throws SQLException{
		String date = getSerialCurrentDate();
		// The key index
		String key_key = (tableName+"_"+pre).toUpperCase();
		String prefix = pre.toUpperCase();

		// Initialize the key table
		if(keys == null){
			keys = new Hashtable();
		}

		String key = (String)keys.get(key_key);
		int newKey = 0;
		
		if(key != null && key.startsWith(date)){
			newKey = Integer.parseInt(key.substring(8))+1;
			key = date + DataFormat.intToString(newKey,6);
			keys.put(key_key,key);
			return prefix + key;
		}

		if(key != null && !key.startsWith(date)){
			keys.remove(key_key);
		}

		String getMaxIDSQL = "SELECT MAX("+keyName+") FROM "+tableName +" WHERE " + 
			keyName + " LIKE '"+prefix+date+"%' ";

		Connection connection = getDataStore().getConnection();
		try {
			/*
			 * 查询最大值
			 */
			Statement stmt = connection.createStatement();
			
			ResultSet resultset = stmt.executeQuery(getMaxIDSQL);
			if(resultset.next()){
				Object maxValue = resultset.getObject(1);
				if(maxValue != null){ 
					key = String.valueOf(maxValue);
				}else{
					key = prefix+date+"000000";
				}
			}else{
				key = prefix+date+"000000";
			}
			resultset.close();
			stmt.close();
		}
		catch(SQLException sqlexception) {
			throw sqlexception;
		}finally{
			getDataStore().freeConnection(connection);
		}

		newKey = Integer.parseInt(key.substring(9))+1;
		key = date + DataFormat.intToString(newKey,6);
		keys.put(key_key,key);

		return prefix + key;
	}
	/**
	 * 返回指定条件的序列号
	 * pre: 序列号的前缀
	 * tableName: 指定的表名称
	 * keyName: 序列号的字段名
	 */
	public synchronized static String getNewPKViaNumber(String tableName, String keyName) throws SQLException{		
		String getMaxIDSQL = "SELECT MAX("+keyName+") FROM "+tableName;
		
		String zero = "0000000000";
		long key=0;
		Connection connection = getDataStore().getConnection();
		try {
			/*
			 * 查询最大值
			 */
			Statement stmt = connection.createStatement();
		
			ResultSet resultset = stmt.executeQuery(getMaxIDSQL);
			if(resultset.next()){
				Object maxValue = resultset.getObject(1);
				if(maxValue != null){ 
					key = Long.parseLong((String)maxValue) + 1;
				}
			}
			resultset.close();
			stmt.close();
		}
		catch(SQLException sqlexception) {
			throw sqlexception;
		}finally{
			getDataStore().freeConnection(connection);
		}

		String skey = Long.toString(key);		
		skey = zero.substring(skey.length())+skey;
		
		return skey;
	}
	
/**
 * 返回当前日期字符串，格式为YYMMDD
 */
public static String getSerialCurrentDate() {
	StringBuffer date = new StringBuffer(getCurrentDate());

	date.deleteCharAt(7);
	date.deleteCharAt(4);

	date.substring(2);
	return date.toString();
}
}