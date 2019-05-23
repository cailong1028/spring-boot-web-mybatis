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
 * 用于实现数据查询的工具类，通过该类和相应的PO对象，实现对单表的查询。 返回ess.base.bo.ESSBean对象(对象集合)。 例如： ESSPL
 * pl = new ESSPL(); ESSPO po = new ... pl.setPO(po); pl.setWhereClause(...);
 * pl.setOrderBy(...); pl.retrieve(); ESSBean beans = pl.getResultList(); int
 * counter = pl.getAvailableCount();
 * 
 * @author Li Shengwang
 * @see ESSPO
 */
public class ESSPL extends ESSObject {

	/**
	 * 查询的PO对象。
	 */
	protected ESSPLQueryInterface PO;

	/**
	 * 查询条件，相当于SQL语句中的WHERE语句。 如设置，不包括'WHERE'关键字。
	 */
	protected String whereClause = null;

	/**
	 * 排序条件，相当于SQL语句中的ORDER BY语句。 如设置，不包括'ORDER BY'关键字。
	 */
	protected String orderBy = null;

	/**
	 * 分组条件，相当于SQL语句中的GROUP BY语句。 如设置，不包括'GROUP BY'关键字。
	 */
	protected String groupBy = null;

	/**
	 * 处理结果集中的记录个数，于startPosition一起确定处理的记录 0指结果中所有的记录。
	 */
	protected int resultSize = 0;

	/**
	 * 结果集处理的起始记录位置，第一条为0
	 */
	protected int startPosition = 0;

	/**
	 * 查询结果列表
	 */
	protected Vector resultList = new Vector();

	/**
	 * 查询结果集是否计数
	 */
	protected boolean countable = true;

	/**
	 * 结果集记录个数计数器
	 */
	protected int recordCounter = 0;

	/**
	 * 指明加Where条件时是否加"WHERE"关键字，true,加，否则不加 不加时，在SQL语句和WHERE条件之间加"AND"关键字
	 */
	protected boolean appendWhere = true;

	/**
	 * The ESSPL constructor
	 */
	public ESSPL() {
	}

	/**
	 * 设置对结果集记录处理的记录个数，0为所有
	 * 
	 * @param size
	 *            The specified size of the result
	 */
	public void setResultSize(int size) {
		resultSize = size;
	}

	/**
	 * 设置对结果集记录处理的起始位置，第一条为0
	 * 
	 * @param startPos
	 *            The specified position from which to retrieve result.
	 */
	public void setStartPosition(int startPos) {
		startPosition = startPos;
	}

	/**
	 * 设置对结果集记录处理的条件，设置改条件结果集将 只返回指定的块的数据
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
	 * 设置查询条件，相当于SQL语句中的WHERE语句。 如设置，不包括"WHERE"关键字。 For example:
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
	 * 设置排序条件，相当于SQL语句中的ORDER BY语句。 如设置，不包括"ORDER BY"关键字。 For example:
	 * setOrderBy("orgid");
	 * 
	 * @param orderBy
	 *            The order condition of the query
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 设置分组条件，相当于SQL语句中的GROUP BY语句。 如设置，不包括"GROUP BY"关键字。 For example:
	 * setGroupBy("orgid,deptno");
	 * 
	 * @param groupBy
	 *            The group by condition of the query
	 */
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	/**
	 * 设置查询的PO对象。
	 * 
	 * @param po
	 *            The database object for query
	 */
	public void setPO(ESSPLQueryInterface po) {
		PO = po;
	}

	/**
	 * 检查是否需要结果集计数
	 * 
	 * @return boolean
	 */
	public boolean isCountable() {
		return countable;
	}

	/**
	 * 设置结果集是否需要计数标识.
	 * 
	 * @param countable
	 *            The countable to set
	 */
	public void setCountable(boolean countable) {
		this.countable = countable;
	}

	/**
	 * 执行查询操作，生成结果集
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
	 * 获取查询结果集。
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
	 * 执行指定的查询SQL语句，返回指定页的数据
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
	 * 执行指定的查询SQL语句，返回指定页的数据
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
	 * 执行指定的查询SQL语句，返回指定页的数据
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
	 * 获取结果集中的记录总个数
	 */
	public int getAvailableCount() {
		return recordCounter;
	}

	/**
	 * 获取查询结果集,返回封装的新式。当改变分页属性(页行数)时，调用 此方法后，要修改结果的页行数属性，通过调用
	 * Result.setResultSize() 进行修改。
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
	 * 执行指定的查询SQL语句，取结果集的第一行、第一列值返回
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
	 * 对结果集当前位置记录处理，生成Bean返回
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
	 * 将对象加入结果列表中
	 * 
	 * @param bean
	 *            The ESSBean object
	 */
	private void addObjectToResult(ESSBean bean) {
		resultList.addElement(bean);
	}
}
