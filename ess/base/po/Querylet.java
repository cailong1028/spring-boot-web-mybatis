/******************************************************************************
    File: Querylet.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
 ******************************************************************************/
package ess.base.po;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import ess.base.bo.DataBean;
import ess.base.bo.ESSBean;
import ess.base.bo.ESSObject;
import ess.base.exp.ESSConnectionGetTimeOutException;
import ess.base.util.Result;

/**
 * 查询辅助类。为了避免基础架构带来的实现复杂查询的难度，本类提供了 实现查询的简单接口。本类可以： (*) 查询返回结果集，直接在使用的地方处理
 * Querylet querylet = new ... java.sql.ResultSet result =
 * querylet.retrieve(sql); querylet.freeAll(); (*)
 * 以ess.base.util.Result的形式返回结果，数据封装成ess.base.bo.DataBean形式的二维结构
 * queryAid.retrieve(sql,page); 在第一种使用方法中，必须调用freeAll方法释放数据库连接资源
 */
public class Querylet extends ESSObject {
	/**
	 * 查询返回的结果中记录的个数。
	 */
	protected int resultSize = 20;

	/**
	 * 返回结构集对象，ResultSet对象
	 */
	ResultSet rs = null;

	/**
	 * 当前数据库连接
	 */
	Connection conn = null;

	/**
	 * Statement 对象
	 */
	Statement stmt = null;

	/**
	 * QueryTools 构造子注解。
	 */
	public Querylet() {
		super();
	}

	/**
	 * 初始化数据库连接和创建Statement对象
	 */
	public void getStatement() throws SQLException, ESSConnectionGetTimeOutException {
		if (session == null)
			conn = getDataStore().getConnection();
		else
			conn = session;
		if (stmt == null)
			stmt = conn.createStatement();
	}

	/**
	 * 执行指定的查询语句，返回结果集
	 */
	public ResultSet retrieve(String sql) throws SQLException, ESSConnectionGetTimeOutException {
		try {
			getStatement();

			rs = stmt.executeQuery(sql);

			return rs;
		} catch (SQLException ex) {
			freeAll();
			log(ex, "Retrieve error!");
			throw ex;
		}
	}

	/**
	 * 执行指定的查询语句，返回指定页的数据
	 */
	public Result retrieve(String sql, int page) throws SQLException, ESSConnectionGetTimeOutException {
		return retrieve(sql, page, true);
	}

	/**
	 * CL 111116 由同名方法替代，此方法注销(新方法中用ESSBean 替代 DataBean)
	 * 执行指定的查询语句，返回指定页的数据
	 */
	/*
	public Result retrieve(String sql, int page, boolean countable) throws SQLException, ESSConnectionGetTimeOutException {
		// Result parameter
		Result rh = new Result();
		rh.setResultSize(resultSize);
		DataBean data = new DataBean();

		// Data parameter
		int currsorPosition = 0;
		int startPosition = rh.getStartPosition(page);
		if (page < 1)
			resultSize = 0;

		try {
			// Execute query
			getStatement();
			rs = stmt.executeQuery(sql);

			// Get column number
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			rsmd = null;

			// Get the result
			while (rs.next()) {
				if (currsorPosition < startPosition) {
					currsorPosition++;
					continue;
				}
				if (resultSize > 0 && (currsorPosition - startPosition == resultSize)) {
					currsorPosition++;
					break;
				}

				DataBean bean = new DataBean();
				for (int i = 1; i <= cols; i++) {
					bean.addObject(rs.getObject(i));
				}
				data.addObject(bean);

				currsorPosition++;
			}

			if (countable) {
				while (rs.next()) {
					currsorPosition++;
				}
			}
		} finally {
			freeAll();
		}

		rh.setBeans(data);
		rh.setCounter(currsorPosition);

		return rh;
	}
	*/
	
	/**
	 * CL 111116 修改 用ESSBean 代替 DataBean
	 * 执行指定的查询语句，返回指定页的数据
	 */
	public Result retrieve(String sql, int page, boolean countable) throws SQLException, ESSConnectionGetTimeOutException {
		// Result parameter
		Result rh = new Result();
		rh.setResultSize(resultSize);
		ESSBean data = new ESSBean();

		// Data parameter
		int currsorPosition = 0;
		int startPosition = rh.getStartPosition(page);
		if (page < 1)
			resultSize = 0;

		try {
			// Execute query
			getStatement();
			rs = stmt.executeQuery(sql);

			// Get column number
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			rsmd = null;

			// Get the result
			while (rs.next()) {
				if (currsorPosition < startPosition) {
					currsorPosition++;
					continue;
				}
				if (resultSize > 0 && (currsorPosition - startPosition == resultSize)) {
					currsorPosition++;
					break;
				}

				ESSBean bean = new ESSBean();
				for (int i = 1; i <= cols; i++) {
					bean.addObjectToList(rs.getObject(i));
				}
				data.addObjectToList(bean);

				currsorPosition++;
			}

			if (countable) {
				while (rs.next()) {
					currsorPosition++;
				}
			}
		} finally {
			freeAll();
		}

		rh.setBeans(data);
		rh.setCounter(currsorPosition);

		return rh;
	}

	/**
	 * 执行指定的查询语句，返回指定页的数据
	 */
	public java.util.Vector retrieveList(String sql) throws SQLException, ESSConnectionGetTimeOutException {
		java.util.Vector data = new java.util.Vector();

		try {
			// Execute query
			getStatement();
			rs = stmt.executeQuery(sql);

			// Get column number
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			rsmd = null;
			java.util.Vector row = null;

			// Get the result
			while (rs.next()) {
				row = new java.util.Vector();
				for (int i = 1; i <= cols; i++) {
					row.addElement(rs.getObject(i));
				}
				data.addElement(row);
			}
		} finally {
			freeAll();
		}

		return data;
	}

	/**
	 * 释放被占用的资源
	 */
	public void freeAll() throws SQLException {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			rs = null;
			stmt = null;
		} finally {
			if (conn != null) {
				if (session == null)
					getDataStore().freeConnection(conn);
			} else {
				// log("释放QueryTools之null的数据库连接");
			}
			conn = null;
			session = null;
		}
	}

	/**
	 * 设置结果集长度。
	 * 
	 * @param newResultSize
	 *            int
	 */
	public void setResultSize(int newResultSize) {
		resultSize = newResultSize;
	}

	/**
	 * 获取当前设置的结果长度。
	 * 
	 * @return int
	 */
	public int getResultSize() {
		return resultSize;
	}
}
