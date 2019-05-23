/******************************************************************************
    File: ESSPO.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
 ******************************************************************************/
package ess.base.po;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ess.base.bo.ESSBean;
import ess.base.exp.ESSConnectionGetTimeOutException;

/**
 * ���ݱ�����ɾ���顢�õĻ������ܵ�ʵ���࣬�������ݱ�Ӧ���� ����Ķ�Ӧ��������ʵ�ֶ����ݱ�Ĳ�����
 */
public abstract class ESSPO extends ESSBean implements ESSPLQueryInterface {

	/**
	 * SQL�е�ѡ��������
	 */
	private String whereClause = null;

	/**
	 * ���ڴ������ݿ���������ݡ�
	 */
	private ESSBean dataBean;

	/**
	 * ָ����Where����ʱ�Ƿ��"WHERE"�ؼ��֣�true,�ӣ����򲻼� ����ʱ����SQL����WHERE����֮���"AND"�ؼ���
	 */
	protected boolean appendWhere = true;

	/**
	 * ʹ��ȱʡ��������洢����
	 */
	public ESSPO() {
	}

	/**
	 * ����������䣬���ڴ����ӵĲ�ѯ������ for example: FIELD1=VALUE1 and FIELD2 = VALUE2;
	 */
	public void setWhereClause(String s) {
		whereClause = s;
	}

	public void setWhereClause(boolean appendWhere, String where) {
		this.appendWhere = appendWhere;
		whereClause = where;
	}

	/**
	 * ���ظ��ӵ��������.
	 */
	public String getWhereClause() {
		return whereClause;
	}

	/**
	 * ����һ����¼,��¼������databeanָ���������ݿ������SQL��̬�����ɡ�
	 */
	public int add() throws SQLException, ESSConnectionGetTimeOutException {

		/*
		 * ���ӵ�������
		 */
		int row = 0;

		/*
		 * ��ȡINSERT SQL
		 */
		String tempSQL = getInsertSQL();

		PreparedStatement ps = null;
		Connection conn = null;
		try {
			if (session == null)
				conn = getDataStore().getConnection();
			else
				conn = session;
			ps = conn.prepareStatement(tempSQL);
			prepareInsertStatement(ps);
			row = ps.executeUpdate();
		} catch (SQLException ex) {
			// getDataStore().getLogWriter().log("Insert error!", ex);
			throw ex;
		} finally {
			if(session==null)
			getDataStore().freeConnection(conn);
			if (ps != null)
				ps.close();
			conn = null;
			session=null;
			tempSQL = null;
		}

		return row;
	}

	/**
	 * ɾ��һ����¼,��¼������databeanָ�������ݿ������SQL��̬���ɡ�
	 */
	public int delete() throws SQLException, ESSConnectionGetTimeOutException {

		// ɾ������
		int row = 0;

		// ��ȡɾ��SQL���
		String sql = getDeleteSQL();

		Statement statement = null;
		Connection conn = null;
		try {
			if (session == null)
				conn = getDataStore().getConnection();
			else
				conn = session;
			statement = conn.createStatement();
			// ����ɾ��������
			row = statement.executeUpdate(sql);
		} catch (SQLException ex) {
			// getDataStore().getLogWriter().log("Delete error:"+sql, ex);
			throw ex;
		} finally {
			if(session==null)
			getDataStore().freeConnection(conn);
			if (statement != null)
				statement.close();
			conn = null;
			session=null;
			sql = null;
		}

		return row;
	}

	/**
	 * �������ݱ��еļ�¼�� ��������������ӦΪ�ա�
	 */
	public int update() throws SQLException, ESSConnectionGetTimeOutException {

		// ���µ���������
		int row = 0;

		/*
		 * ��ȡ���²��������SQL��䡣
		 */
		String sql = getUpdateSQL();

		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// ������̬SQL����
			if (session == null)
				conn = getDataStore().getConnection();
			else
				conn = session;
			ps = conn.prepareStatement(sql);
			// ʵ������̬SQL.
			prepareUpdateStatement(ps);
			// ִ�и��²���
			row = ps.executeUpdate();
		} catch (SQLException ex) {
			// getDataStore().getLogWriter().log("Update error!", ex);
			throw ex;
		} finally {
			if(session==null)
			getDataStore().freeConnection(conn);

			if (ps != null)
				ps.close();
			conn = null;
			session=null;
			sql = null;
		}

		return row;
	}

	/**
	 * ִ�����ݸ��²���������SQLֱ���ɲ���ָ����
	 */
	public int executeUpdate(String sql) throws SQLException, ESSConnectionGetTimeOutException {

		// ��������
		int row = 0;

		Statement statement = null;
		Connection conn = null;
		try {
			if (session == null)
				conn = getDataStore().getConnection();
			else
				conn = session;
			statement = conn.createStatement();
			// ����ɾ��������
			row = statement.executeUpdate(sql);
		} catch (SQLException ex) {
			// getDataStore().getLogWriter().log("Update error:"+sql, ex);
			throw ex;
		} finally {
			if (session == null)
			getDataStore().freeConnection(conn);
			if (statement != null)
				statement.close();
			conn = null;
			session=null;
			sql = null;
		}

		return row;
	}

	// -----------------���·�����Ҫ����----------------------
	/**
	 * �������ݿ������ for example: return "DEPARTMENT";
	 */
	public abstract String getTableName();

	/**
	 * �����ֶ���ϡ� for example:return "FIELD1,FIELD2,..."
	 */
	public abstract String getColumnNames();

	/**
	 * ����ֵ�����,����ʹ��һ�ֶ�̬SQL��䣬��ִ��ʱ����������SQL��䡣 for example: return "?,?,?,..."
	 * 
	 * @see #prepareInsertStatement(PreparedStatement)
	 */
	public abstract String getInsertValuesString();

	/**
	 * �����ַ����µ�ֵ�� for example: return "FIELD1=?,FIELD2=?,...";
	 * 
	 * @see #prepareUpdateStatement(PreparedStatement)
	 */
	public abstract String getUpdateValuesString();

	/**
	 * �����ѯ֮����������������Ǳ�����DataBean�С�
	 * 
	 * for example:dataBean. Organization org = new Organization(); ...
	 * org.setAttrName(resultset.getDataType(COLUMN-NAME));
	 * //org.setAttrName(resultset.getDataType(columnIndexStart +
	 * COLUMN-INDEX)); ... //setDataBean(org); return org;
	 * 
	 * ע�⣺COLUMN-NAME ��ʽΪ ColumnName �� Department��� DEPTNO �е� COLUMN-NAME Ϊ��
	 * "DEPTNO"
	 * 
	 * ��ע���÷����ڶ���ѯʱ���ܴ������⣬�����д���֤���Ե����ѯ�� ���������⡣����ò�������ţ�column-index��ȡֵ������һ��
	 * �������⡣���ڵĽ���������£� ��ԭ���ķ���
	 * org.setAttrName(resultset.getDataType(COLUMN-INDEX)); ��Ϊ
	 * org.setAttrName(resultset.getDataType(columnIndexStart + COLUMN-INDEX));
	 * 
	 * �Զ���ѯʱ����������������ᷢ�����󣺾�����ͬ�����Ƶ��ֶ� ������ͬ��ֵ��
	 */
	public abstract ESSBean fetchResult(ResultSet rs) throws SQLException;

	/**
	 * ͨ������DataBean,ʵ�ֶ�̬����SQL��ʵ������
	 * 
	 * for example: Organization dataBean = (Organization)getDataBean();
	 * ps.setString(2,dataBean.getName()); ...
	 * 
	 * @see #getInsertValuesString()
	 */
	public abstract void prepareInsertStatement(PreparedStatement ps) throws SQLException;

	/**
	 * ͨ������DataBean,ʵ�ֶ�̬����SQL��ʵ������
	 * 
	 * for example: Organization dataBean = (Organization)getDataBean();
	 * ps.setString(2,dataBean.getName()); ...
	 * 
	 * @see #getUpdateValuesString()
	 */
	public abstract void prepareUpdateStatement(PreparedStatement ps) throws SQLException;

	/**
	 * �������ݶ���
	 */
	public ESSBean getDataBean() {
		// return this.dataBean;
		return this;
	}

	/**
	 * �������ݶ���
	 */
	public void setDataBean(ESSBean dataBean) {
		this.dataBean = dataBean;
	}

	/**
	 * ����ɾ��SQL��䡣����PO���ã��Զ�ƴװɾ��SQL��䡣 �÷���������д��ʵ������ɾ����������Ҫ��ֱ��Ƕ��SQL��䡣
	 * 
	 * @see #getColumnNames()
	 * @see #getTableName()
	 * @see #delete()
	 * 
	 * @return DELETE SQL
	 */
	public String getDeleteSQL() {
		// ��̬ƴװɾ��SQL���
		StringBuffer tempSQL = new StringBuffer(256);
		tempSQL.append("DELETE FROM ");
		tempSQL.append(getTableName());

		if (whereClause != null) {
			tempSQL.append(" WHERE ");

			// �ⲿ���ý�����������䡣
			tempSQL.append(getWhereClause());
		}

		return tempSQL.toString();
	}

	/**
	 * ���ز���SQL��䡣����PO���ã��Զ�ƴװ����SQL��䡣 �÷���������д��ʵ�����Ĳ��뷽������Ҫ��ֱ��Ƕ��SQL��䡣
	 * 
	 * @see #getColumnNames()
	 * @see #getTableName()
	 * @see #getInsertValuesString()
	 * @see #add()
	 * 
	 * @return INSERT SQL
	 */
	public String getInsertSQL() {
		/*
		 * ��̬���SQL���:INERT INTO TABLENAME (FIELD1,FIELD2,...)
		 * VALUES(FIELD1VALUE,FIELD2VALUE,...) TABLENAME:getTableName();
		 * FIELD1,FIELD2,...:getColumnNames();
		 * FIELDVALUE1,FIELDVALUE2,...:getInsertValuesString();
		 */
		StringBuffer tempSQL = new StringBuffer(256);
		tempSQL.append("INSERT INTO ");
		tempSQL.append(getTableName());
		tempSQL.append(" (");
		tempSQL.append(getColumnNames());
		tempSQL.append(") ");
		tempSQL.append("VALUES(" + getInsertValuesString() + ") ");

		return tempSQL.toString();
	}

	/**
	 * ���ز�ѯSQL��䡣����PO���ã��Զ�ƴװ��ѯSQL��䡣 �÷���������д��ʵ�����Ĳ�ѯ���������뷽��fetchResult()
	 * Ӧ��Ӧ����Ҫ��ֱ��Ƕ��SQL��䡣
	 * 
	 * @see #getColumnNames()
	 * @see #getTableName()
	 * @see #fetchResult(ResultSet)
	 * @see #retrieve()
	 * 
	 * @return QUERY SQL
	 */
	public String getQuerySQL() {
		StringBuffer SQL = new StringBuffer(300);
		SQL.append("SELECT ");
		SQL.append(getColumnNames());
		SQL.append(" FROM ");
		SQL.append(getTableName());

		return SQL.toString();
	}

	/**
	 * ���ظ���SQL��䡣����PO���ã��Զ�ƴװ����SQL��䡣 �÷���������д��ʵ�����ĸ��·�������Ҫ��ֱ��Ƕ��SQL��䡣
	 * 
	 * @see #getColumnNames()
	 * @see #getTableName()
	 * @see #getUpdateValuesString()
	 * @see #update()
	 * 
	 * @return UPDATE SQL
	 */
	public String getUpdateSQL() {
		/*
		 * ��̬ƴװ���²��������SQL��䡣
		 */
		StringBuffer tempSQL = new StringBuffer(256);

		tempSQL.append("UPDATE ");
		tempSQL.append(getTableName());
		tempSQL.append(" SET ");
		tempSQL.append(getUpdateValuesString());

		if (whereClause != null) {
			tempSQL.append(" WHERE ");
			tempSQL.append(getWhereClause());
		}

		return tempSQL.toString();
	}

	/**
	 * �����ݱ��в���ָ���ļ�¼�����ض�Ӧ�Ķ���һ�����ڵ��� ����Ĳ�ѯ����ѯ�������ɷ��� setWhereCaluse()ָ���������
	 * Ϊ�����¼��ֻ���ص�һ����¼������Ľ����ԡ�
	 * 
	 * @return com.ess.base.bo.ESSBean ���������null��˵��Ϊ�鵽
	 */
	public ESSBean retrieve() throws SQLException, ESSConnectionGetTimeOutException {
		String sql = getQuerySQL();

		if (whereClause != null && whereClause.length() > 0) {
			if (appendWhere) {
				sql += " WHERE ";
			} else {
				sql += " AND ";
			}
			sql += whereClause;
		}

		Connection conn = null;
		Statement statement = null;
		ResultSet resultset = null;

		try {
			if (session == null)
				conn = getDataStore().getConnection();
			else
				conn = session;

			statement = conn.createStatement();
			resultset = statement.executeQuery(sql);

			// Fetch the result
			if (resultset.next()) {
				return fetchResult(resultset);
			} else {
				return null;
			}
		} catch (SQLException ex1) {
			throw ex1;
		} finally {
			if (resultset != null)
				try {
					resultset.close();
				} catch (SQLException ex) {
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException ex) {
				}
			if (session == null)
				getDataStore().freeConnection(conn);
			conn = null;
			session = null;
		}
	}

	public ESSBean retrieve(String whereClause, boolean appendWhere) throws SQLException, ESSConnectionGetTimeOutException {
		this.whereClause = whereClause;
		this.appendWhere = appendWhere;
		return retrieve();
	}
}
