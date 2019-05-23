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
 * ��ѯ�����ࡣΪ�˱�������ܹ�������ʵ�ָ��Ӳ�ѯ���Ѷȣ������ṩ�� ʵ�ֲ�ѯ�ļ򵥽ӿڡ�������ԣ� (*) ��ѯ���ؽ������ֱ����ʹ�õĵط�����
 * Querylet querylet = new ... java.sql.ResultSet result =
 * querylet.retrieve(sql); querylet.freeAll(); (*)
 * ��ess.base.util.Result����ʽ���ؽ�������ݷ�װ��ess.base.bo.DataBean��ʽ�Ķ�ά�ṹ
 * queryAid.retrieve(sql,page); �ڵ�һ��ʹ�÷����У��������freeAll�����ͷ����ݿ�������Դ
 */
public class Querylet extends ESSObject {
	/**
	 * ��ѯ���صĽ���м�¼�ĸ�����
	 */
	protected int resultSize = 20;

	/**
	 * ���ؽṹ������ResultSet����
	 */
	ResultSet rs = null;

	/**
	 * ��ǰ���ݿ�����
	 */
	Connection conn = null;

	/**
	 * Statement ����
	 */
	Statement stmt = null;

	/**
	 * QueryTools ������ע�⡣
	 */
	public Querylet() {
		super();
	}

	/**
	 * ��ʼ�����ݿ����Ӻʹ���Statement����
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
	 * ִ��ָ���Ĳ�ѯ��䣬���ؽ����
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
	 * ִ��ָ���Ĳ�ѯ��䣬����ָ��ҳ������
	 */
	public Result retrieve(String sql, int page) throws SQLException, ESSConnectionGetTimeOutException {
		return retrieve(sql, page, true);
	}

	/**
	 * CL 111116 ��ͬ������������˷���ע��(�·�������ESSBean ��� DataBean)
	 * ִ��ָ���Ĳ�ѯ��䣬����ָ��ҳ������
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
	 * CL 111116 �޸� ��ESSBean ���� DataBean
	 * ִ��ָ���Ĳ�ѯ��䣬����ָ��ҳ������
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
	 * ִ��ָ���Ĳ�ѯ��䣬����ָ��ҳ������
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
	 * �ͷű�ռ�õ���Դ
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
				// log("�ͷ�QueryTools֮null�����ݿ�����");
			}
			conn = null;
			session = null;
		}
	}

	/**
	 * ���ý�������ȡ�
	 * 
	 * @param newResultSize
	 *            int
	 */
	public void setResultSize(int newResultSize) {
		resultSize = newResultSize;
	}

	/**
	 * ��ȡ��ǰ���õĽ�����ȡ�
	 * 
	 * @return int
	 */
	public int getResultSize() {
		return resultSize;
	}
}
