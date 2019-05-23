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
 * �˽ӿ��ṩͨ��������ʵ�����ݿ��ѯ�ķ�����ʵ�����ݿ��ѯ�������ʵ�ִ˽ӿڡ�
 * ����ʵ�ִ˽ӿ�ʱ�����ɵ�����ESSPL����Ӧ�ķ���ʵ�����ݿ����ݲ�ѯ��
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
 * �������ڣ�(2001-6-27 19:53:04)
 * @author Li Shengwang
 */
public interface ESSPLQueryInterface {
    /**
     * ��ȡ��¼����ǰλ�õ����ݣ��������ݷ�װ��JavaBean���ء�
     * 
     * @return com.ESS.base.bo.ESSBean �������
     * @param rs java.sql.ResultSet  ��¼��
     */
    ESSBean fetchResult(ResultSet rs) throws SQLException;

    /**
     * ��ȡ�����Ĳ�ѯSQL��䣬��������ѯ������
     * for example:
     *    return "SELECT field-list FROM organization,employee ";
     *
     * @return java.lang.String ����SQL��䡣
     */
    String getQuerySQL();
}
