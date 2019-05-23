/******************************************************************************
    File: ESSBatch.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/
package ess.base.po;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.BatchUpdateException;

import ess.base.bo.ESSBean;
import ess.base.bo.ESSObject;
import ess.base.po.ESSPO;

/**
 * ʵ��������Ļ����ࡣ�������ESSPO��ʵ������ɾ���ĵ������������
 *
 * @see ESSPO
 */
public class ESSBatch extends ESSObject {
    /**
     * ESSBatch ������ע�⡣
     */
    public ESSBatch() {
        super();
    }

    /**
     * �������������ִ��ָ���������������
     *
     * @param sqls Ҫִ�е�������SQL�������
     * @return int[] ������ִ�н��
     * @exception SQLException ���������������ݿ����ʱ�׳�
     */
    public int[] executeBatch(String[] sqls) throws SQLException{
        Statement stmt = null;
        Connection conn = null;
        try{
            conn = getDataStore().getConnection();
            if(conn == null){
                log("Get connection error!");
                throw new java.sql.SQLException("Get Connection error! Can't get a connection��");
            }

            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            for(int i=0;sqls != null && i<sqls.length; i++){
                if(sqls[i] != null && sqls[i].length()>0){
                    stmt.addBatch(sqls[i]);
                }
            }
            int[] rows = stmt.executeBatch();

            return rows;
        }catch(BatchUpdateException bue) {
            String err = "������ִ�н��:\n";
            int [] updateCounts = bue.getUpdateCounts();
            for (int i=0; i<updateCounts.length; i++) {
                err += updateCounts[i] + "->";
            }
            err += "\n";
            log(err);
            return updateCounts;
        }catch(SQLException ex){
            log(ex,"Batch execute error!");
            throw ex;
        }catch(Exception ex){
            log(ex,"������ִ�д���");
            throw new java.sql.SQLException("ִ�в�������ʱ����");
        }finally{
            if(stmt != null){
                stmt.close();
            }
            if(conn != null){
                conn.setAutoCommit(true);
            }
            getDataStore().freeConnection(conn);
        }
    }

    /**
     * ���������������������PO�еĲ���ָ����ͨ��po.getDataBean().getBeans()��ȡ
     * ��������������Ĳ�����Ŀǰֻ֧��ͬһ����������������
     * ע�⣺���׳�BatchUpdateExceptionʱ�������
     *        int[] updateCounts = BatchUpdateException.getUpdateCounts()
     * ȷ���ɹ������������updateCounts.length����
     *
     * @return int[] ���µļ�¼�����飬�������˳�����С�
     */
    public int[] add(ESSPO po) throws java.sql.SQLException {
        PreparedStatement ps = null;
        Connection conn = null;
        try{
            conn = getDataStore().getConnection();
            if(conn == null){
                log("Get connection error!");
                throw new java.sql.SQLException("Get Connection error! Can't get a connection��");
            }

            conn.setAutoCommit(false);
            ps = conn.prepareStatement(po.getInsertSQL());

            ESSBean bean = po.getDataBean();
            ESSBean[] beans = bean.getBeans();
            for(int i=0;i<beans.length; i++){
                po.setDataBean(beans[i]);
                po.prepareInsertStatement(ps);
                ps.addBatch();
            }

            int[] rows = ps.executeBatch();

            return rows;
        }catch(SQLException ex){
            log(ex,"Batch update error!");
            throw ex;
        }catch(Exception ex){
            ex.printStackTrace();
            throw new java.sql.SQLException("ִ�в�������ʱ����");
        }finally{
            getDataStore().freeConnection(conn);
            if(ps != null){
                ps.close();
            }
            if(conn != null){
                conn.setAutoCommit(true);
            }
        }
    }

    /**
     * ɾ�������������������PO�еĲ���ָ����ͨ��po.getDataBean().getBeans()��ȡ
     * ��������������Ĳ�����
     */
    public int[] delete(ESSPO[] pos) throws java.sql.SQLException {
        Statement stmt = null;
        Connection conn = null;
        try{
            conn = getDataStore().getConnection();
            if(conn == null){
                log("Get connection error!");
                throw new java.sql.SQLException("Get connection error��Can't get a connection");
            }

            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            for(int i=0;i<pos.length; i++){
                stmt.addBatch(pos[i].getDeleteSQL());
            }

            int[] rows = stmt.executeBatch();

            return rows;
        }catch(SQLException ex){
            ex.printStackTrace();
            throw ex;
        }catch(Exception ex){
            log(ex,"ִ�в���������ʱ����");
            throw new java.sql.SQLException("ִ�в���������ʱ����");
        }finally{
            getDataStore().freeConnection(conn);
            if(stmt != null){
                stmt.close();
            }
            if(conn != null){
                conn.setAutoCommit(true);
                conn = null;
            }
        }
    }

    /**
     * ���������������������PO�еĲ���ָ����ͨ��po.getDataBean().getBeans()��ȡ
     * ��������������Ĳ�����Ŀǰֻ֧��ͬһ����������������
     * ע�⣺���׳�BatchUpdateExceptionʱ�������
     *        int[] updateCounts = BatchUpdateException.getUpdateCounts()
     * ȷ���ɹ������������updateCounts.length����
     *
     * @return int[] ���µļ�¼�����飬�������˳�����С�
     */
    public int[] update(ESSPO po) throws java.sql.SQLException {
        PreparedStatement ps = null;
        Connection conn = null;
        try{
            conn = getDataStore().getConnection();
            if(conn == null){
                log("Get connection error!");
                throw new java.sql.SQLException("Get Connection error! Can't get a connection��");
            }

            conn.setAutoCommit(false);
            ps = conn.prepareStatement(po.getUpdateSQL());

            ESSBean bean = po.getDataBean();
            ESSBean[] beans = bean.getBeans();
            for(int i=0;i<beans.length; i++){
                po.setDataBean(beans[i]);
                po.prepareUpdateStatement(ps);
                ps.addBatch();
            }

            int[] rows = ps.executeBatch();

            return rows;
        }catch(SQLException ex){
            log(ex,"Batch update error!");
            throw ex;
        }catch(Exception ex){
            ex.printStackTrace();
            throw new java.sql.SQLException("ִ�в�������ʱ����");
        }finally{
            getDataStore().freeConnection(conn);
            if(ps != null){
                ps.close();
            }
            if(conn != null){
                conn.setAutoCommit(true);
            }
        }
    }
}
