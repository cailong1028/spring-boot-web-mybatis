/******************************************************************************
    File ESSObject.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003      Lishengwang    Created

    Copyright (c), 2003
    all rights reserved
******************************************************************************/
package ess.base.bo;

import java.io.Serializable;
import java.sql.Connection;

import ess.base.db.ESSDataStoreManager;
import ess.base.util.LogWriter;

/**
 * 平台为支持外部存储的目的是为了实现平台实例的外部存储，将来这个平台
 * 可能同时运行多个实例。
 * 平台使用的唯一一个数据源管理对象，存放在此是为了随时可以取得这个对象，以兼容多种
 * 数据源访问方式，当然平台推荐的数据库访问还是在BO及BO以下，主要是在PO中完成。
 */
public class ESSObject implements Serializable {
	
	/**
	 * 数据库连接
	 */
	protected Connection session = null;

	public Connection getSession() {
		return session;
	}

	public void setSession(Connection session) {
		this.session = session;
	}

    /**
     * ESSObject 构造子注解。
     */
    public ESSObject() {
        super();
    }

    /**
     *　返回平台的数据存储对象。
     */
    public static ESSDataStoreManager getDataStore() {
        return ESSDataStoreManager.getInstance();
    }

    /**
     * 返回当前的日志对象。
     */
    public static LogWriter getLogWriter() {
        return LogWriter.getInstance();
    }

    /**
     * 添加消息到平台缺省的日志文件。
     */
    public void log(String msg) {
        getLogWriter().log(this,msg,LogWriter.INFO);
    }

    /**
     * 添加消息到平台缺省的日志文件，并且显示错误跟踪信息。
     */
    public void log(Throwable t, String msg) {
        getLogWriter().log(this,t,msg,LogWriter.ERROR);
    }

    /**
     * 将指定级别的消息写道到平台缺省的日志位置。
     */
    public void log(String msg, int logLevel) {
        getLogWriter().log(this,msg,logLevel);
    }

    /**
     * 设置默认数据库。
     */
    public void setPreferredDBName(String defaultDBName) {
        getDataStore().setPreferredDBName(defaultDBName);
    }
}
