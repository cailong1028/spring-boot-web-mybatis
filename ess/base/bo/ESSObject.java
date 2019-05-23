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
 * ƽ̨Ϊ֧���ⲿ�洢��Ŀ����Ϊ��ʵ��ƽ̨ʵ�����ⲿ�洢���������ƽ̨
 * ����ͬʱ���ж��ʵ����
 * ƽ̨ʹ�õ�Ψһһ������Դ������󣬴���ڴ���Ϊ����ʱ����ȡ����������Լ��ݶ���
 * ����Դ���ʷ�ʽ����Ȼƽ̨�Ƽ������ݿ���ʻ�����BO��BO���£���Ҫ����PO����ɡ�
 */
public class ESSObject implements Serializable {
	
	/**
	 * ���ݿ�����
	 */
	protected Connection session = null;

	public Connection getSession() {
		return session;
	}

	public void setSession(Connection session) {
		this.session = session;
	}

    /**
     * ESSObject ������ע�⡣
     */
    public ESSObject() {
        super();
    }

    /**
     *������ƽ̨�����ݴ洢����
     */
    public static ESSDataStoreManager getDataStore() {
        return ESSDataStoreManager.getInstance();
    }

    /**
     * ���ص�ǰ����־����
     */
    public static LogWriter getLogWriter() {
        return LogWriter.getInstance();
    }

    /**
     * �����Ϣ��ƽ̨ȱʡ����־�ļ���
     */
    public void log(String msg) {
        getLogWriter().log(this,msg,LogWriter.INFO);
    }

    /**
     * �����Ϣ��ƽ̨ȱʡ����־�ļ���������ʾ���������Ϣ��
     */
    public void log(Throwable t, String msg) {
        getLogWriter().log(this,t,msg,LogWriter.ERROR);
    }

    /**
     * ��ָ���������Ϣд����ƽ̨ȱʡ����־λ�á�
     */
    public void log(String msg, int logLevel) {
        getLogWriter().log(this,msg,logLevel);
    }

    /**
     * ����Ĭ�����ݿ⡣
     */
    public void setPreferredDBName(String defaultDBName) {
        getDataStore().setPreferredDBName(defaultDBName);
    }
}
