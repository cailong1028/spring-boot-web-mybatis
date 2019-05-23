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
 * 实现批处理的基础类。该类基于ESSPO，实现增、删、改的批处理操作。
 *
 * @see ESSPO
 */
public class ESSBatch extends ESSObject {
    /**
     * ESSBatch 构造子注解。
     */
    public ESSBatch() {
        super();
    }

    /**
     * 混合批处理方法，执行指定的批处理操作。
     *
     * @param sqls 要执行的批处理SQL语句数组
     * @return int[] 批处理执行结果
     * @exception SQLException 当发生批处理数据库错误时抛出
     */
    public int[] executeBatch(String[] sqls) throws SQLException{
        Statement stmt = null;
        Connection conn = null;
        try{
            conn = getDataStore().getConnection();
            if(conn == null){
                log("Get connection error!");
                throw new java.sql.SQLException("Get Connection error! Can't get a connection！");
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
            String err = "批处理执行结果:\n";
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
            log(ex,"批处理执行错误");
            throw new java.sql.SQLException("执行插入事务时错误！");
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
     * 插入批处理。批处理参数在PO中的参数指定，通过po.getDataBean().getBeans()获取
     * 所有用于批处理的参数。目前只支持同一个表的批处理操作。
     * 注意：当抛出BatchUpdateException时，请调用
     *        int[] updateCounts = BatchUpdateException.getUpdateCounts()
     * 确定成功的命令个数（updateCounts.length）。
     *
     * @return int[] 更新的记录数数组，以命令的顺序排列。
     */
    public int[] add(ESSPO po) throws java.sql.SQLException {
        PreparedStatement ps = null;
        Connection conn = null;
        try{
            conn = getDataStore().getConnection();
            if(conn == null){
                log("Get connection error!");
                throw new java.sql.SQLException("Get Connection error! Can't get a connection！");
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
            throw new java.sql.SQLException("执行插入事务时错误！");
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
     * 删除批处理。批处理参数在PO中的参数指定，通过po.getDataBean().getBeans()获取
     * 所有用于批处理的参数。
     */
    public int[] delete(ESSPO[] pos) throws java.sql.SQLException {
        Statement stmt = null;
        Connection conn = null;
        try{
            conn = getDataStore().getConnection();
            if(conn == null){
                log("Get connection error!");
                throw new java.sql.SQLException("Get connection error！Can't get a connection");
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
            log(ex,"执行插入批处理时错误！");
            throw new java.sql.SQLException("执行插入批处理时错误！");
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
     * 更新批处理。批处理参数在PO中的参数指定，通过po.getDataBean().getBeans()获取
     * 所有用于批处理的参数。目前只支持同一个表的批处理操作。
     * 注意：当抛出BatchUpdateException时，请调用
     *        int[] updateCounts = BatchUpdateException.getUpdateCounts()
     * 确定成功的命令个数（updateCounts.length）。
     *
     * @return int[] 更新的记录数数组，以命令的顺序排列。
     */
    public int[] update(ESSPO po) throws java.sql.SQLException {
        PreparedStatement ps = null;
        Connection conn = null;
        try{
            conn = getDataStore().getConnection();
            if(conn == null){
                log("Get connection error!");
                throw new java.sql.SQLException("Get Connection error! Can't get a connection！");
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
            throw new java.sql.SQLException("执行插入事务时错误！");
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
