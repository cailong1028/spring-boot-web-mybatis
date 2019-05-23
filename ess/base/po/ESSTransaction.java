/******************************************************************************
    File: ESSTransaction.java

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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import ess.base.bo.ESSObject;
import ess.base.bo.DataBean;
import ess.base.exp.ESSConnectionGetTimeOutException;

/**
 * ʵ��������Ļ����ࡣ������ڻ���PO���ʵ�֣��������ɾ�� �ò������������С� ����Ҫִ��������sql1,sql2,... //��ʼ����
 * ESSTranaction trans = new ... trans.begin(); trans.executeUpdate(sql1);
 * trans.executeUpdate(sql2); ... //�ύ���� trans.commit();
 * 
 * ��Ҫע�⣬���׳���SQLExceptionʱ��Ҫִ�лع�������
 */
public class ESSTransaction extends ESSObject {

	private Connection connection = null;

	/**
	 * ESSTransaction ������ע�⡣
	 */
	public ESSTransaction() {
		super();
	}

	/**
	 * ����Transaction������Connection�Զ��ύ����Ϊfalse��
	 */
	public void begin() throws java.sql.SQLException, ESSConnectionGetTimeOutException {

		if (session == null)
			connection = getDataStore().getConnection();
		else
			connection = session;

		if (connection != null) {
			try {
				connection.setAutoCommit(false);
			} catch (SQLException ex) {
				if (session == null)
					getDataStore().freeConnection(connection);
				connection = null;
				session = null;
				throw ex;
			}
		} else {
			log("Transaction begin for get connection error!");
			throw new java.sql.SQLException("Get transaction connection error��");
		}

	}

	/**
	 * Submit the transaction.
	 */
	public void commit() throws SQLException {
		try {
			connection.commit();
			connection.setAutoCommit(true);
			if (session == null)
				getDataStore().freeConnection(connection);

			connection = null;
			session = null;
		} catch (SQLException ex) {
			rollback();
			log(ex, "Transaction commit error!");
			throw ex;
		}
	}

	/**
	 * ����������
	 */
	public int add(ESSPO[] poList) throws java.sql.SQLException {
		try {
			int row = 0;
			for (int i = 0; i < poList.length; i++) {
				PreparedStatement ps = connection.prepareStatement(poList[i].getInsertSQL());
				poList[i].prepareInsertStatement(ps);
				row += ps.executeUpdate();

				ps.close();
			}
			return row;
		} catch (SQLException ex) {
			rollback();
			log(ex.getMessage());
			throw ex;
		} catch (Throwable ex) {
			rollback();
			log(ex, "Transaction insert error!");
			throw new java.sql.SQLException("Transaction add error:" + ex.getMessage());
		}

	}

	/**
	 * ����������
	 */
	public int add(ESSPO po) throws java.sql.SQLException {
		if (po == null) {
			return 0;
		}

		try {
			int row = 0;
			ess.base.bo.ESSBean[] pos = po.getBeans();

			PreparedStatement ps = connection.prepareStatement(po.getInsertSQL());
			for (int i = 0; i < pos.length; i++) {
				((ESSPO) pos[i]).prepareInsertStatement(ps);
				row += ps.executeUpdate();
			}

			ps.close();

			return row;
		} catch (SQLException ex) {
			rollback();
			log(ex, "Transaction insert error!");
			throw ex;
		} catch (Throwable ex) {
			rollback();
			log(ex, "Transaction insert error!");
			throw new java.sql.SQLException("Transaction add error:" + ex.getMessage());
		}
	}

	/**
	 * ����������
	 */
	public int addNoRollback(ESSPO po) throws java.sql.SQLException {
		if (po == null) {
			return 0;
		}

		try {
			int row = 0;
			ess.base.bo.ESSBean[] pos = po.getBeans();

			PreparedStatement ps = connection.prepareStatement(po.getInsertSQL());
			for (int i = 0; i < pos.length; i++) {
				((ESSPO) pos[i]).prepareInsertStatement(ps);
				row += ps.executeUpdate();
			}

			ps.close();

			return row;
		} catch (SQLException ex) {
			log(ex, "Transaction insert error!");
			throw ex;
		} catch (Throwable ex) {
			log(ex, "Transaction insert error!");
			throw new java.sql.SQLException("Transaction add error:" + ex.getMessage());
		}
	}

	/**
	 * ����������
	 */
	public int update(ESSPO[] poList) throws java.sql.SQLException {
		try {
			int row = 0;
			for (int i = 0; i < poList.length; i++) {
				PreparedStatement ps = connection.prepareStatement(poList[i].getUpdateSQL());
				poList[i].prepareUpdateStatement(ps);
				row += ps.executeUpdate();

				ps.close();
			}
			return row;
		} catch (SQLException ex) {
			rollback();
			log(ex, "Transaction update error!");
			throw ex;
		} catch (Throwable ex) {
			rollback();
			log(ex, "Transaction update error!");
			throw new java.sql.SQLException("Transaction update error:" + ex.getMessage());
		}
	}

	/**
	 * ����������
	 */
	public int update(ESSPO po) throws java.sql.SQLException {
		if (po == null) {
			return 0;
		}

		try {
			int row = 0;
			ess.base.bo.ESSBean[] pos = po.getBeans();
			PreparedStatement ps = connection.prepareStatement(po.getUpdateSQL());

			for (int i = 0; i < pos.length; i++) {
				((ESSPO) pos[i]).prepareUpdateStatement(ps);
				row += ps.executeUpdate();
			}

			ps.close();

			return row;
		} catch (SQLException ex) {
			rollback();
			log(ex, "Transaction update error!");
			throw ex;
		} catch (Throwable ex) {
			rollback();
			log(ex, "Transaction update error!");
			throw new java.sql.SQLException("Transaction update error��");
		}
	}

	/**
	 * ɾ��������
	 */
	public int delete(ESSPO[] poList) throws java.sql.SQLException {
		try {
			Statement statement = connection.createStatement();

			// ����ɾ��������
			int row = 0;
			for (int i = 0; i < poList.length; i++) {
				row += statement.executeUpdate(poList[i].getDeleteSQL());
			}
			statement.close();
			return row;
		} catch (SQLException ex) {
			rollback();
			log(ex, "Transaction delete error!");
			throw ex;
		} catch (Throwable ex) {
			rollback();
			log(ex, "Transaction delete error!");
			throw new java.sql.SQLException("Transaction delete error:" + ex.getMessage());
		}
	}

	/**
	 * ɾ��������
	 */
	public int delete(ESSPO po) throws java.sql.SQLException {
		if (po == null) {
			return 0;
		}

		try {
			Statement statement = connection.createStatement();

			// ����ɾ��������
			int row = statement.executeUpdate(po.getDeleteSQL());

			statement.close();

			return row;
		} catch (SQLException ex) {
			rollback();
			log(ex, "Transaction delete error!");
			throw ex;
		} catch (Throwable ex) {
			rollback();
			log(ex, "Transaction delete error!");
			throw new java.sql.SQLException("Transaction delete error:" + ex.getMessage());
		}
	}

	/**
	 * ����������
	 */
	public int executeUpdate(String sql) throws java.sql.SQLException {
		if (sql == null) {
			return 0;
		}

		try {
			Statement statement = connection.createStatement();
			int row = statement.executeUpdate(sql);

			statement.close();
			return row;
		} catch (SQLException ex) {
			rollback();
			log(ex, "Transaction update error!");
			throw ex;
		} catch (Throwable ex) {
			rollback();
			log(ex, "Transaction update error!");
			throw new java.sql.SQLException("Transaction update error:" + ex.getMessage());
		}
	}

	/**
	 * ����������,ͬʱִ�ж������¡�
	 */
	public int executeUpdate(String[] sqls) throws java.sql.SQLException {
		try {
			Statement statement = connection.createStatement();

			int row = 0;
			for (int i = 0; sqls != null && i < sqls.length; i++) {
				if (sqls[i] != null && !sqls[i].equals("")) {
					// System.out.println(sqls[i]);
					row += statement.executeUpdate(sqls[i]);
				}
			}

			statement.close();
			return row;
		} catch (SQLException ex) {
			rollback();
			log(ex, "Transaction update error!");
			throw ex;
		}
	}

	/**
	 * ִ��ָ���Ĳ�ѯSQL��䣬ȡ������ĵ�һ�С���һ��ֵ����
	 */
	public Object retrieve(String sql) throws SQLException {
		if (sql == null) {
			return null;
		}

		try {
			Object obj = null;
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);

			if (result.next()) {
				obj = result.getObject(1);
			}

			result.close();
			stmt.close();

			return obj;
		} catch (SQLException ex) {
			rollback();
			log(ex, "Transaction retrieve error!");
			throw ex;
		}
	}

	/**
	 * ִ�������ѯ������ָ�������Ľ������
	 * 
	 * @param sql
	 *            ��ѯSQL���
	 * @param counter
	 *            ���ؽ���������������������С�ڴ���ʱ��ȫ�����أ��� counter <= 0ʱ������ȫ�����
	 * @return DataBean ���ؽ�������д����DataBean�У�ÿ��Ϊһ��DataBean����ȫ�� �������һ��DataBean ��
	 *         int DataBean.size(); ��ȡ������� DataBean DataBean.getObject(int)
	 *         ���ص�i�����ݣ���0��ʼ����
	 */
	public DataBean retrieve(String sql, int counter) throws java.sql.SQLException {
		try {
			// Result parameter
			DataBean data = new DataBean();

			// Data parameter
			int currsorPosition = 0;

			// Execute query
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);

			// Get column number
			ResultSetMetaData rsmd = result.getMetaData();
			int cols = rsmd.getColumnCount();
			rsmd = null;

			// Get the result
			while (result.next()) {
				if (counter > 0 && (currsorPosition >= counter)) {
					break;
				}

				DataBean bean = new DataBean();
				for (int i = 1; i <= cols; i++) {
					bean.addObject(result.getObject(i));
				}
				data.addObject(bean);

				currsorPosition++;
			}
			stmt.close();

			return data;
		} catch (SQLException ex) {
			rollback();
			log(ex, "Transaction retrieve error!");
			throw ex;
		}
	}

	/**
	 * Submit the transaction.
	 */
	public void rollback() throws SQLException {
		try {
			if (connection != null) {
				connection.rollback();
			}
			// connection.setAutoCommit(true);
		} catch (SQLException ex) {
			getDataStore().getLogWriter().log("Transaction rollback error:", ex);
			throw ex;
		} finally {
			if (connection != null) {
				connection.setAutoCommit(true);
				if (session == null)
					getDataStore().freeConnection(connection);
				connection = null;
				session = null;
			}
		}
	}
}
