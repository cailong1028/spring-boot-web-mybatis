/******************************************************************************
    File: DataSourceEx.java

    Version 1.0
    Date            Author                Changes
    Jul.04,2003     Li Shengwang          Created

    Copyright (c) 2003, Eagle Soar
    all rights reserved.
******************************************************************************/
package ess.base.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * �����ṩ�����ݿ���ʵ�����Դ�ӿ�
 */
public interface ESSDataSource extends DataSource{
    /**
     * �ͷ����ݿ�����
     * @param conn Ҫ�ͷŵ�����
     */
    void freeConnection(Connection conn) throws SQLException;

    /**
     * ��ȡ����Դ������Ϣ
     * @return String ����Դ������Ϣ
     */
    String monitor();
}
