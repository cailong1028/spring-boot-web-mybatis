/******************************************************************************
    File: ESSPJL.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/
package ess.base.po;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

import ess.base.bo.*;
import ess.base.exp.ESSConnectionGetTimeOutException;

/**
 * ����ʵ�����ݲ�ѯ�Ĺ����࣬ʹ�ø����ָ���ľ��й�����ϵ��PO��ʵ�ֹ�����ѯ��
 * ����ʵ���ϵĸ����ԣ�������ʹ�ø÷�����
 */
public class ESSPJL extends ESSPL {

    ESSPO[] POs = null;

    String outerJoinClause = "";

    public boolean distinct = true;    

    /**
     * ���캯����
     */
    public ESSPJL() {
    }

    /**
     * Allows clients to perform outer joins. A sample clause would look like
     * " A LEFT OUTER JOIN B ON A.X = B.Y ". when a left outer join is to
     * be performed between tables A and B and A.X = B.Y is the join condition.
     * Note that the client can add additional search restrictions through the
     * setWhereClause method. Also note that once you specify the outer join
     * clause, all the names of the tables that are to be joined must appear in
     * it (whether they are involved in the outer join or not).
     * @param newOuterJoinClause String
     */
    public void setOuterJoinClause(String newOuterJoinClause) {
        outerJoinClause = newOuterJoinClause;
    }

    /**
     * ������ʵ�ֶ������Ӳ�ѯ����ѯ�Ľ���洢��һ��������ΪԪ�ص������С�
     */
    public int getAvailableCount(){
/*        int count = 0;
        // clear all result list.
        if (resultList != null)
            resultList.removeAllElements();

        // the number of tables being joined
        int poNum = POs.length;
        StringBuffer SQL = new StringBuffer(300);
        SQL.append("SELECT ");
        if (distinct) {
            SQL.append(" DISTINCT ");
        }

        SQL.append("COUNT(*)");

        SQL.append(" FROM ");
        if (outerJoinClause.length() > 0)
            SQL.append(outerJoinClause);
        else {
            for (int i = 0; i < poNum; i++) {
                SQL.append(POs[i].getTableName());
                if (i != poNum - 1) {
                    SQL.append(" , ");
                }
            }
        }

        if (whereClause.length() > 0) {
            SQL.append(" WHERE ");
            SQL.append(whereClause);
        }
        if (orderBy.length() > 0) {
            SQL.append(" ORDER BY ");
            SQL.append(orderBy);
        }
        if (groupBy.length() > 0) {
            SQL.append(" GROUP BY ");
            SQL.append(groupBy);
        }

        int endPosition = startPosition + resultSize;

        SQL.append(" FETCH FIRST " + endPosition + " ROWS ONLY");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            // We will prepare this statement so that the database statement cache can be more effective
            conn = getDataStore().getConnection();
            stmt = conn.prepareStatement(SQL.toString());
            rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1); // get the count
            }
        } catch (SQLException e) {
            throw new Exception("Execute query from multiple POs error:" + SQL);
        } finally {
            rs.close();
            stmt.close();
            getDataStore().freeConnection(conn);
        }
        return count;
*/
        return recordCounter;
    }

    /**
     * ������ʵ�ֶ������Ӳ�ѯ����ѯ�Ľ���洢��һ��������ΪԪ�ص������С�
     */
    public void retrieve() throws SQLException,ESSConnectionGetTimeOutException{
        // clear all result list.
        if (resultList != null)
            resultList.removeAllElements();

        // the number of tables being joined
        int poNum = POs.length;
        StringBuffer SQL = new StringBuffer(300);
        SQL.append("SELECT ");
        if (distinct) {
            SQL.append(" DISTINCT ");
        }

        /*
         * �ڶ���ѯʱ�����������ֶ����ظ���������������������SQL���ʱ����Ҫ
         * ÿ���ֶ�������Ψһ��־����ʱ��õİ취��ÿ���ֶ���������������
         * �����ò���ȡ������
         */

        //ѡ�����е�����ֵ������ļ��������е����Բ�ͬͬ����
        for (int i = 0; i < poNum; i++) {
            if (i != 0) {
                SQL.append(" , ");
            }
            SQL.append(POs[i].getColumnNames());
        }

        SQL.append(" FROM ");
        if (outerJoinClause.length() > 0)
            SQL.append(outerJoinClause);
        else {
            for (int i = 0; i < poNum; i++) {
                SQL.append(POs[i].getTableName());
                if (i != poNum - 1) {
                    SQL.append(" , ");
                }
            }
        }
        // { Li Shengwang begin }
        if (whereClause != null && whereClause.length() > 0) {
            SQL.append(" WHERE ");
            SQL.append(whereClause);
        }
        if (orderBy != null && orderBy.length() > 0) {
            SQL.append(" ORDER BY ");
            SQL.append(orderBy);
        }
        if (groupBy != null && groupBy.length() > 0) {
            SQL.append(" GROUP BY ");
            SQL.append(groupBy);
        }
        //if (resultSize > 0) {
            //int endPosition = startPosition + resultSize;

            //SQL.append(" FETCH FIRST " + endPosition + " ROWS ONLY");
        //}

        // { Li Shengwang end }

        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            // We will prepare this statement so that the database statement cache can be more effective
            conn = getDataStore().getConnection();
            stmt = conn.prepareStatement(SQL.toString());
            rs = stmt.executeQuery();
            fetchResults(rs);
        } catch (ESSConnectionGetTimeOutException ex) {
            throw ex;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            stmt.close();
            getDataStore().freeConnection(conn);
        }
    }

    /**
     * �����ѯ֮����������������ǻ�ԭ������PO�С�
     */
    public void fetchResults(ResultSet rs) throws SQLException {
        int poNum = POs.length;
        Class[] referenceClasses = new Class[poNum];
        for (int i = 0; i < poNum; i++) {
            referenceClasses[i] = POs[i].getClass();
        }
        int cursorPosition = 0;

        // the column from which we fetch the result
        //int[] columnIndexStart = new int[poNum];
        //boolean columnIndexStartSetted = true;
        //int tempIndexStart = 0;

        // Retrieve the result set
        while (rs.next()) {
            /*
             * start instantiating objects only from the point needed
             */
            if (cursorPosition < startPosition) {
                cursorPosition++;
                continue;
            }
            if(resultSize > 0 && (cursorPosition - startPosition == resultSize)){
                cursorPosition++;
                break;
            }
            // represents a single row in the join results
            ESSBean[] objArray = new ESSBean[poNum];
            ESSPO nextObject = null;

            // loop through each of the reference objects
            for (int i = 0; i < poNum; i++) {
                // Instantiate the object for the table in the
                //join only if requested
                // create a new instance of one of the reference objects
                try {
                    nextObject = (ESSPO) referenceClasses[i].newInstance();
                } catch (Exception ex) {
                }
                // ask it to fetch the join results
                //if(columnIndexStartSetted){
                //    columnIndexStart[i] = tempIndexStart;
                //}
                //nextObject.setColumnIndexStart(columnIndexStart[i]);
                ESSBean bean = nextObject.fetchResult(rs);

                // add the base column
                //tempIndexStart += nextObject.getColumnNum();

                // add it to set that represents a single row from the join
                // results 
                objArray[i] = bean;
            }
            // add it to the list of rows
            this.addToResultList(objArray);
            cursorPosition++;
        }

        while (rs.next()) {
            cursorPosition++;
        }

        recordCounter = cursorPosition;
    }

    /**
     * ����ʵ�����Ӳ�ѯ��PO,������Ҫ����PO����ʵ�ֲ�ѯ�� 
     *
     * @param refObjs ESSPersistentObject[]
     */
    public void setPOs(ESSPO[] refObjs) {
        this.POs = refObjs;
    }

    public ESSBean[][] getResultList2() {
        /*
                ESSPO[][] beans = new ESSBean[resultList.size()][POs.length];
                resultList.copyInto(poss);
                return poss;
        */
        ESSBean[][] beans = new ESSBean[resultList.size()][POs.length];
        for (int index = 0; index < resultList.size(); index++) {
            ESSBean[] bean = (ESSBean[]) (resultList.elementAt(index));
            for (int col = 0; col < bean.length; col++) {
                beans[index][col] = bean[col];
            }
        }
        return beans;
    }

    /**
     * Adds an array representing one row of the join result into the result
     * set
     */
    public void addToResultList(ESSBean[] row) {
        resultList.addElement(row);
    }
    
    /**
     * ���ý�����Ƿ�ȥ���ظ���¼������
     */    
    public void setDistinct(boolean newDistinct) {
        distinct = newDistinct;
    }
}
