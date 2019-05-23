/******************************************************************************
    File: ESSPLQueryInterface.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/
package ess.base.po;


import java.sql.ResultSet;
import java.sql.SQLException;

import ess.base.bo.ESSBean;

/**
 * 此接口提供通过基础类实现数据库查询的方法，实现数据库查询的类必须实现此接口。
 * 当类实现此接口时，即可调用类ESSPL中相应的方法实现数据库数据查询。
 * for example:
 *   The PO implementation
 *     public class XXXPO implements ESSPLQueryInterface{
 *         ......
 *         ......
 *     }
 *
 *  The Query method
 *  {
 *     // Construct the PO Object
 *     XXXPO po = new XXXPO();
 *     // Construct the ESSPL Object
 *     ESSPL pl = new ESSPL();
 *     // Set the query condition such as where,orderby,groupby,etc.
 *     pl.setWhereClause(...); // Exclude the key word "WHERE"
 *     pl.setGroupBy(...);     // Exclude the key word "GROUP BY"
 *     pl.setOrderBy(...);     // Exclude the key word "ORDER BY"
 *     // Set the result parameter, such as the result size and the
 *     // start position from which fetch the result set
 *     pl.setStartPosition(0); // From the first record
 *     pl.setResultSize(20);   // Only get the 20 records
 *     // Set the PO and retrieve the result
 *     pl.setQueryPO(po);
 *     pl.retrieve();
 *     // Get the result list
 *     ESSBean[] beans = pl.getResultList();
 *  }
 *
 * 创建日期：(2001-6-27 19:53:04)
 * @author Li Shengwang
 */
public interface ESSPLQueryInterface {
    /**
     * 获取记录集当前位置的数据，并将数据封装成JavaBean返回。
     * 
     * @return com.ESS.base.bo.ESSBean 结果对象
     * @param rs java.sql.ResultSet  记录集
     */
    ESSBean fetchResult(ResultSet rs) throws SQLException;

    /**
     * 获取基本的查询SQL语句，不包括查询条件。
     * for example:
     *    return "SELECT field-list FROM organization,employee ";
     *
     * @return java.lang.String 基本SQL语句。
     */
    String getQuerySQL();
}
