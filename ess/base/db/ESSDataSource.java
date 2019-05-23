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
 * 本类提供了数据库访问的数据源接口
 */
public interface ESSDataSource extends DataSource{
    /**
     * 释放数据库连接
     * @param conn 要释放的连接
     */
    void freeConnection(Connection conn) throws SQLException;

    /**
     * 获取数据源连接信息
     * @return String 数据源连接信息
     */
    String monitor();
}
