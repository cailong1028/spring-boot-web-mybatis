/******************************************************************************
    File: ESSPL.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
 ******************************************************************************/
package ess.base.po;

import java.util.Vector;
import java.sql.*;

import ess.base.bo.ESSBean;
import ess.base.bo.ESSObject;
import ess.base.exp.ESSConnectionGetTimeOutException;
import ess.base.util.Result;

/**
 * ����ʵ�����ݲ�ѯ�Ĺ����࣬ͨ���������Ӧ��PO����ʵ�ֶԵ���Ĳ�ѯ�� ����ess.base.bo.ESSBean����(���󼯺�)�� ���磺 ESSPL
 * pl = new ESSPL(); ESSPO po = new ... pl.setPO(po); pl.setWhereClause(...);
 * pl.setOrderBy(...); pl.retrieve(); ESSBean beans = pl.getResultList(); int
 * counter = pl.getAvailableCount();
 * 
 * @author Li Shengwang
 * @see ESSPO
 */
public class ESSPL extends ESSObject {

	/**
	 * ��ѯ��PO����
	 */
	protected ESSPLQueryInterface PO;

	/**
	 * ��ѯ�������൱��SQL����е�WHERE��䡣 �����ã�������'WHERE'�ؼ��֡�
	 */
	protected String whereClause = null;

	/**
	 * �����������൱��SQL����е�ORDER BY��䡣 �����ã�������'ORDER BY'�ؼ��֡�
	 */
	protected String orderBy = null;

	/**
	 * �����������൱��SQL����е�GROUP BY��䡣 �����ã�������'GROUP BY'�ؼ��֡�
	 */
	protected String groupBy = null;

	/**
	 * ���������еļ�¼��������startPositionһ��ȷ������ļ�¼ 0ָ��������еļ�¼��
	 */
	protected int resultSize = 0;

	/**
	 * ������������ʼ��¼λ�ã���һ��Ϊ0
	 */
	protected int startPosition = 0;

	/**
	 * ��ѯ����б�
	 */
	protected Vector resultList = new Vector();

	/**
	 * ��ѯ������Ƿ����
	 */
	protected boolean countable = true;

	/**
	 * �������¼����������
	 */
	protected int recordCounter = 0;

	/**
	 * ָ����Where����ʱ�Ƿ��"WHERE"�ؼ��֣�true,�ӣ����򲻼� ����ʱ����SQL����WHERE����֮���"AND"�ؼ���
	 */
	protected boolean appendWhere = true;

	/**
	 * The ESSPL constructor
	 */
	public ESSPL() {
	}

	/**
	 * ���öԽ������¼����ļ�¼������0Ϊ����
	 * 
	 * @param size
	 *            The specified size of the result
	 */
	public void setResultSize(int size) {
		resultSize = size;
	}

	/**
	 * ���öԽ������¼�������ʼλ�ã���һ��Ϊ0
	 * 
	 * @param startPos
	 *            The specified position from which to retrieve result.
	 */
	public void setStartPosition(int startPos) {
		startPosition = startPos;
	}

	/**
	 * ���öԽ������¼��������������ø������������ ֻ����ָ���Ŀ������
	 * 
	 * @param size
	 *            The specified size of the result or page, The value should >
	 *            0.
	 * @param page
	 *            The specified page no to retrieve The value should > 0.
	 * @see #setResultSize(int)
	 * @see #setStartPosition(int)
	 */
	public void setResultPage(int size, int page) {
		if (page > 0 && size > 0) {
			setResultSize(size);
			setStartPosition(size * (page - 1));
		}
	}

	/**
	 * ���ò�ѯ�������൱��SQL����е�WHERE��䡣 �����ã�������"WHERE"�ؼ��֡� For example:
	 * setWhereClause("orgid='88889999'");
	 * 
	 * @param where
	 *            The query condition
	 */
	public void setWhereClause(String where) {
		whereClause = where;
	}

	public void setWhereClause(boolean appendWhere, String where) {
		this.appendWhere = appendWhere;
		whereClause = where;
	}

	/**
	 * ���������������൱��SQL����е�ORDER BY��䡣 �����ã�������"ORDER BY"�ؼ��֡� For example:
	 * setOrderBy("orgid");
	 * 
	 * @param orderBy
	 *            The order condition of the query
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * ���÷����������൱��SQL����е�GROUP BY��䡣 �����ã�������"GROUP BY"�ؼ��֡� For example:
	 * setGroupBy("orgid,deptno");
	 * 
	 * @param groupBy
	 *            The group by condition of the query
	 */
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	/**
	 * ���ò�ѯ��PO����
	 * 
	 * @param po
	 *            The database object for query
	 */
	public void setPO(ESSPLQueryInterface po) {
		PO = po;
	}

	/**
	 * ����Ƿ���Ҫ���������
	 * 
	 * @return boolean
	 */
	public boolean isCountable() {
		return countable;
	}

	/**
	 * ���ý�����Ƿ���Ҫ������ʶ.
	 * 
	 * @param countable
	 *            The countable to set
	 */
	public void setCountable(boolean countable) {
		this.countable = countable;
	}

	/**
	 * ִ�в�ѯ���������ɽ����
	 */
	public void retrieve() throws SQLException, ESSConnectionGetTimeOutException {
		resultList.removeAllElements();
		StringBuffer SQL = new StringBuffer(300);
		// SQL.append("SELECT ");
		// if (distinct) {
		// SQL.append("DISTINCT ");
		// }
		SQL.append(PO.getQuerySQL());

		if (whereClause != null && whereClause.length() > 0) {
			if (appendWhere) {
				SQL.append(" WHERE ");
			} else {
				SQL.append(" AND ");
			}
			SQL.append(whereClause);
		}

		if (groupBy != null && groupBy.length() > 0) {
			SQL.append(" GROUP BY ");
			SQL.append(groupBy);
		}
		if (orderBy != null && orderBy.length() > 0) {
			SQL.append(" ORDER BY ");
			SQL.append(orderBy);
		}

		// if(resultSize > 0){
		// int endPosition = startPosition+resultSize;
		// SQL.append(" FETCH FIRST " + endPosition + " ROWS ONLY");
		// }

		Connection conn = null;
		Statement statement = null;
		ResultSet resultset = null;

		try {
			if (session == null)
				conn = getDataStore().getConnection();
			else
				conn = session;
			statement = conn.createStatement();
			resultset = statement.executeQuery(SQL.toString());

			this.fetchResults(resultset);
		} catch (SQLException ex1) {
			getDataStore().getLogWriter().log("Retrieve Error:" + SQL, ex1);
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

	/**
	 * ��ȡ��ѯ�������
	 * 
	 * @return null if the resultset is empty or bean. If returned ESSBean, get
	 *         the bean list from the ESSBean as following: ESSBean[] beans =
	 *         ESSBean.getBeans();
	 */
	public ESSBean getResultList() {
		// ESSBean[] beans = new ESSBean[resultList.size()];
		// resultList.copyInto(beans);

		// return beans;

		ESSPO po = null;
		ESSBean bean = null;
		// if (resultList.size() == 1) {
		// bean = (ESSBean) resultList.elementAt(0);
		// } else{
		if (resultList.size() > 0) {
			bean = (ESSBean) resultList.elementAt(0);
			bean.addBeanToList(bean);
			for (int index = 1; index < resultList.size(); index++) {
				bean.addBeanToList((ESSBean) resultList.elementAt(index));
			}
		}
		// }

		return bean;
	}

	/**
	 * ִ��ָ���Ĳ�ѯSQL��䣬����ָ��ҳ������
	 * 
	 * @param sql
	 *            The query SQL
	 * @param po
	 *            The PO Object
	 */
	public Result retrieve(String sql, ESSPLQueryInterface po) throws SQLException, ESSConnectionGetTimeOutException {
		return retrieve(sql, po, 0, 0);
	}

	/**
	 * ִ��ָ���Ĳ�ѯSQL��䣬����ָ��ҳ������
	 * 
	 * @param sql
	 *            The query SQL
	 * @param po
	 *            The PO Object
	 * @param page
	 *            The specified page no
	 */
	public Result retrieve(String sql, ESSPLQueryInterface po, int page) throws SQLException, ESSConnectionGetTimeOutException {
		return retrieve(sql, po, Result.DEFAULT_PAGE_SIZE, page);
	}

	/**
	 * ִ��ָ���Ĳ�ѯSQL��䣬����ָ��ҳ������
	 * 
	 * @param sql
	 *            The query SQL
	 * @param po
	 *            The PO Object
	 * @param pageSize
	 *            The size of the page
	 * @param page
	 *            The specified page no
	 */
	public Result retrieve(String sql, ESSPLQueryInterface po, int pageSize, int page) throws SQLException, ESSConnectionGetTimeOutException {
		Connection conn = null;
		Statement statement = null;
		ResultSet resultset = null;

		try {
			setResultPage(pageSize, page);
			this.PO = po;

			if (session == null)
				conn = getDataStore().getConnection();
			else
				conn = session;
			statement = conn.createStatement();
			resultset = statement.executeQuery(sql);

			// Fetch result
			fetchResults(resultset);
			return getResult();
		} catch (SQLException ex1) {
			// getDataStore().getLogWriter().log("Retrieve Error:"+sql, ex1);
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

	/**
	 * ��ȡ������еļ�¼�ܸ���
	 */
	public int getAvailableCount() {
		return recordCounter;
	}

	/**
	 * ��ȡ��ѯ�����,���ط�װ����ʽ�����ı��ҳ����(ҳ����)ʱ������ �˷�����Ҫ�޸Ľ����ҳ�������ԣ�ͨ������
	 * Result.setResultSize() �����޸ġ�
	 * 
	 * @return Result includes the result list and the total counter of the
	 *         query result
	 * @see ess.base.util.Result
	 */
	public Result getResult() {
		ESSBean beans = getResultList();
		Result result = new Result();

		result.setBeans(beans);
		result.setCounter(getAvailableCount());
		result.setStartPosition(startPosition);
		if (resultSize > 0) {
			result.setResultSize(resultSize);
		}

		return result;
	}

	/**
	 * ִ��ָ���Ĳ�ѯSQL��䣬ȡ������ĵ�һ�С���һ��ֵ����
	 * 
	 * @param sql
	 *            The query SQL
	 */
	public Object retrieve(String sql) throws SQLException, ESSConnectionGetTimeOutException {
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
			if (resultset.next()) {
				return resultset.getObject(1);
			} else {
				return null;
			}
		} catch (SQLException ex1) {
			// getDataStore().getLogWriter().log("Retrieve Error:"+sql, ex1);
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

	/**
	 * �Խ������ǰλ�ü�¼��������Bean����
	 * 
	 * @param rs
	 *            The result set of the query
	 */
	private void fetchResults(ResultSet rs) throws SQLException {
		int currsorPosition = 0;
		while (rs.next()) {
			if (currsorPosition < startPosition) {
				currsorPosition++;
				continue;
			}
			if (resultSize > 0 && (currsorPosition - startPosition == resultSize)) {
				currsorPosition++;
				break;
			}

			ESSBean bean = PO.fetchResult(rs);
			addObjectToResult(bean);
			currsorPosition++;
		}

		while (rs.next() && countable) {
			currsorPosition++;
		}

		recordCounter = currsorPosition;
	}

	/**
	 * ������������б���
	 * 
	 * @param bean
	 *            The ESSBean object
	 */
	private void addObjectToResult(ESSBean bean) {
		resultList.addElement(bean);
	}
}
