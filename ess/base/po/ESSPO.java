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
 * 数据表增、删、查、该的基本功能的实现类，所有数据表都应生成 该类的对应子类用于实现对数据表的操作。
 */
public abstract class ESSPO extends ESSBean implements ESSPLQueryInterface {

	/**
	 * SQL中的选择条件。
	 */
	private String whereClause = null;

	/**
	 * 用于传递数据库操作的数据。
	 */
	private ESSBean dataBean;

	/**
	 * 指明加Where条件时是否加"WHERE"关键字，true,加，否则不加 不加时，在SQL语句和WHERE条件之间加"AND"关键字
	 */
	protected boolean appendWhere = true;

	/**
	 * 使用缺省参数构造存储对象。
	 */
	public ESSPO() {
	}

	/**
	 * 设置条件语句，用于处理复杂的查询条件。 for example: FIELD1=VALUE1 and FIELD2 = VALUE2;
	 */
	public void setWhereClause(String s) {
		whereClause = s;
	}

	public void setWhereClause(boolean appendWhere, String where) {
		this.appendWhere = appendWhere;
		whereClause = where;
	}

	/**
	 * 返回复杂的条件语句.
	 */
	public String getWhereClause() {
		return whereClause;
	}

	/**
	 * 插入一条记录,记录数据由databean指定，而数据库操作的SQL动态的生成。
	 */
	public int add() throws SQLException, ESSConnectionGetTimeOutException {

		/*
		 * 增加的列数。
		 */
		int row = 0;

		/*
		 * 获取INSERT SQL
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
	 * 删除一条记录,记录数据由databean指定，数据库操作的SQL动态生成。
	 */
	public int delete() throws SQLException, ESSConnectionGetTimeOutException {

		// 删除行数
		int row = 0;

		// 获取删除SQL语句
		String sql = getDeleteSQL();

		Statement statement = null;
		Connection conn = null;
		try {
			if (session == null)
				conn = getDataStore().getConnection();
			else
				conn = session;
			statement = conn.createStatement();
			// 返回删除的行数
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
	 * 更新数据表中的记录。 更新搜索条件不应为空。
	 */
	public int update() throws SQLException, ESSConnectionGetTimeOutException {

		// 更新的数据行数
		int row = 0;

		/*
		 * 获取更新操作所需的SQL语句。
		 */
		String sql = getUpdateSQL();

		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// 创建动态SQL对象
			if (session == null)
				conn = getDataStore().getConnection();
			else
				conn = session;
			ps = conn.prepareStatement(sql);
			// 实例化动态SQL.
			prepareUpdateStatement(ps);
			// 执行更新操作
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
	 * 执行数据更新操作，更新SQL直接由参数指定。
	 */
	public int executeUpdate(String sql) throws SQLException, ESSConnectionGetTimeOutException {

		// 更新行数
		int row = 0;

		Statement statement = null;
		Connection conn = null;
		try {
			if (session == null)
				conn = getDataStore().getConnection();
			else
				conn = session;
			statement = conn.createStatement();
			// 返回删除的行数
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

	// -----------------以下方法需要重载----------------------
	/**
	 * 返回数据库表名。 for example: return "DEPARTMENT";
	 */
	public abstract String getTableName();

	/**
	 * 返回字段组合。 for example:return "FIELD1,FIELD2,..."
	 */
	public abstract String getColumnNames();

	/**
	 * 返回值的组合,这里使用一种动态SQL语句，在执行时生成真正的SQL语句。 for example: return "?,?,?,..."
	 * 
	 * @see #prepareInsertStatement(PreparedStatement)
	 */
	public abstract String getInsertValuesString();

	/**
	 * 返回字符更新的值。 for example: return "FIELD1=?,FIELD2=?,...";
	 * 
	 * @see #prepareUpdateStatement(PreparedStatement)
	 */
	public abstract String getUpdateValuesString();

	/**
	 * 处理查询之后的运算结果，将他们保存在DataBean中。
	 * 
	 * for example:dataBean. Organization org = new Organization(); ...
	 * org.setAttrName(resultset.getDataType(COLUMN-NAME));
	 * //org.setAttrName(resultset.getDataType(columnIndexStart +
	 * COLUMN-INDEX)); ... //setDataBean(org); return org;
	 * 
	 * 注意：COLUMN-NAME 格式为 ColumnName 如 Department表的 DEPTNO 列的 COLUMN-NAME 为：
	 * "DEPTNO"
	 * 
	 * 备注：该方法在多表查询时可能存在问题，现在有待论证，对单表查询则 不会有问题。但最好不用列序号（column-index）取值，这样一定
	 * 存在问题。现在的解决方案如下： 将原来的方法
	 * org.setAttrName(resultset.getDataType(COLUMN-INDEX)); 改为
	 * org.setAttrName(resultset.getDataType(columnIndexStart + COLUMN-INDEX));
	 * 
	 * 对多表查询时，存在下列情况不会发生错误：具有相同列名称的字段 具有相同的值。
	 */
	public abstract ESSBean fetchResult(ResultSet rs) throws SQLException;

	/**
	 * 通过参数DataBean,实现动态插入SQL的实例化。
	 * 
	 * for example: Organization dataBean = (Organization)getDataBean();
	 * ps.setString(2,dataBean.getName()); ...
	 * 
	 * @see #getInsertValuesString()
	 */
	public abstract void prepareInsertStatement(PreparedStatement ps) throws SQLException;

	/**
	 * 通过参数DataBean,实现动态更新SQL的实例化。
	 * 
	 * for example: Organization dataBean = (Organization)getDataBean();
	 * ps.setString(2,dataBean.getName()); ...
	 * 
	 * @see #getUpdateValuesString()
	 */
	public abstract void prepareUpdateStatement(PreparedStatement ps) throws SQLException;

	/**
	 * 返回数据对象
	 */
	public ESSBean getDataBean() {
		// return this.dataBean;
		return this;
	}

	/**
	 * 设置数据对象
	 */
	public void setDataBean(ESSBean dataBean) {
		this.dataBean = dataBean;
	}

	/**
	 * 返回删除SQL语句。根据PO配置，自动拼装删除SQL语句。 该方法可以重写，实现灵活的删除方法。主要是直接嵌入SQL语句。
	 * 
	 * @see #getColumnNames()
	 * @see #getTableName()
	 * @see #delete()
	 * 
	 * @return DELETE SQL
	 */
	public String getDeleteSQL() {
		// 动态拼装删除SQL语句
		StringBuffer tempSQL = new StringBuffer(256);
		tempSQL.append("DELETE FROM ");
		tempSQL.append(getTableName());

		if (whereClause != null) {
			tempSQL.append(" WHERE ");

			// 外部设置进来的条件语句。
			tempSQL.append(getWhereClause());
		}

		return tempSQL.toString();
	}

	/**
	 * 返回插入SQL语句。根据PO配置，自动拼装插入SQL语句。 该方法可以重写，实现灵活的插入方法。主要是直接嵌入SQL语句。
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
		 * 动态组合SQL语句:INERT INTO TABLENAME (FIELD1,FIELD2,...)
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
	 * 返回查询SQL语句。根据PO配置，自动拼装查询SQL语句。 该方法可以重写，实现灵活的查询方法，但与方法fetchResult()
	 * 应对应。主要是直接嵌入SQL语句。
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
	 * 返回更新SQL语句。根据PO配置，自动拼装更新SQL语句。 该方法可以重写，实现灵活的更新方法。主要是直接嵌入SQL语句。
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
		 * 动态拼装更新操作所需的SQL语句。
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
	 * 在数据表中查找指定的记录，返回对应的对象。一般用于单个 对象的查询。查询的条件由方法 setWhereCaluse()指定，若结果
	 * 为多个记录，只返回第一个记录，其余的将忽略。
	 * 
	 * @return com.ess.base.bo.ESSBean 结果，返回null则说明为查到
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
