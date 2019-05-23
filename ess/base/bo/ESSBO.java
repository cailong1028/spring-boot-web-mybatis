/******************************************************************************
    File ESSBO.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003
    all rights reserved
 ******************************************************************************/
package ess.base.bo;

import java.sql.SQLException;

import ess.base.exp.*;
import ess.base.po.ESSPL;
import ess.base.po.ESSPLQueryInterface;
import ess.base.util.Result;

/**
 * 业务逻辑类基类，提供了业务逻辑处理的基本接口
 */
public class ESSBO extends ESSObject {
	/**
	 * 构造函数,象这样的构造函数中，最好不要带参数，因为会给子类对象 带来不方便。
	 */
	public ESSBO() {
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
		ESSPL pl = new ESSPL();
		return pl.retrieve(sql, po, pageSize, page);
	}

	/**
	 * 查询指定条件的对象集合
	 * 
	 * @param po
	 *            查询的对象，提供查询用基本SQL语句
	 * @param whereClause
	 *            查询条件
	 * @param page
	 *            查询页数,0为不分页
	 * @return ess.base.util.Result 查询结果
	 * @see #retrieve(ESSPLQueryInterface, String, boolean, String, int)
	 */
	public ess.base.util.Result retrieve(ESSPLQueryInterface po, String whereClause, int page) throws java.sql.SQLException {
		return retrieve(po, whereClause, true, null, page);
	}

	/**
	 * 查询指定条件的对象集合
	 * 
	 * @param po
	 *            查询的对象，提供查询用基本SQL语句
	 * @param whereClause
	 *            查询条件
	 * @param orderBy
	 *            排序条件，不含"ORDER BY"关键字
	 * @param page
	 *            查询页数,0为不分页
	 * @return ess.base.util.Result 查询结果
	 * @see #retrieve(ESSPLQueryInterface,String,boolean,String,int)
	 */
	public ess.base.util.Result retrieve(ESSPLQueryInterface po, String whereClause, String orderBy, int page) throws java.sql.SQLException {
		return retrieve(po, whereClause, true, orderBy, page);
	}

	/**
	 * 查询指定条件的对象集合, 以Bean集合方式返回，不处理分页
	 * 
	 * @param po
	 *            查询的对象，提供查询用基本SQL语句
	 * @param whereClause
	 *            查询条件
	 * @param orderBy
	 *            排序条件，不含"ORDER BY"关键字
	 * @return ess.base.bo.ESSBean 查询结果,没有时返回空
	 */
	public ess.base.bo.ESSBean retrieve(ESSPLQueryInterface po, String whereClause, String orderBy) throws java.sql.SQLException {
		ess.base.po.ESSPL pl = new ess.base.po.ESSPL();
		pl.setSession(session);
		pl.setPO(po);
		pl.setWhereClause(whereClause);
		pl.setOrderBy(orderBy);
		pl.retrieve();

		return pl.getResultList();
	}

	/**
	 * 查询指定条件的对象集合
	 * 
	 * @param po
	 *            查询的对象，提供查询用基本SQL语句
	 * @param whereClause
	 *            查询条件
	 * @param orderBy
	 *            排序条件，不含"ORDER BY"关键字
	 * @param appendWhere
	 *            是否加"WHERE"关键字，true,加WHERE关键字，否则 加"AND"关键字
	 * @param page
	 *            查询页数,0为不分页
	 * @return ess.base.util.Result 查询结果
	 */
	public ess.base.util.Result retrieve(ESSPLQueryInterface po, String whereClause, boolean appendWhere, String orderBy, int page) throws java.sql.SQLException {
		ess.base.util.Result result = new ess.base.util.Result();
		ess.base.po.ESSPL pl = new ess.base.po.ESSPL();
		pl.setSession(session);
		pl.setPO(po);
		pl.setWhereClause(appendWhere, whereClause);
		pl.setOrderBy(orderBy);
		pl.setResultPage(result.getResultSize(), page);
		pl.retrieve();
	
		result = pl.getResult();
		return result;
	}

	/**
	 * 执行数据更新操作，更新SQL直接由参数指定。
	 */
	public int executeUpdate(String sql) throws SQLException, ESSConnectionGetTimeOutException {
		// 更新行数
		int row = 0;
		java.sql.Statement statement = null;
		java.sql.Connection conn = null;
		try {
			if (session == null)
				conn = getDataStore().getConnection();
			else
				conn = session;
			statement = conn.createStatement();
			// 返回删除的行数
			row = statement.executeUpdate(sql);
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (session == null)
				getDataStore().freeConnection(conn);
			if (statement != null)
				statement.close();
			conn = null;
			session = null;
			sql = null;
		}

		return row;
	}

	/**
	 * 本方法是一个不可见命令，其作用是根据当前用户标识和接口名称，从数据库中 查询接口的具体实现。
	 */
	protected final ESSCmd createCommand(String commandName) {

		/*
		 * ECSDataBean[] outBeans = null; try { GetCommandCmd getCommand = null;
		 * //本功能接口为实现功能接口查询的基础类，无需再次通过数据库查询。 getCommand = new
		 * GetCommandCmdImpl(); //查询条件首先是保存在Bean中的。 CommandBean bean = new
		 * CommandBean(); bean.setCommandName(commandName); int orgRefNum = -1;
		 * if(context!=null) { orgRefNum =
		 * context.getOrganization().getReferenceNumber(); }
		 * bean.setOrganizationReferenceNumber(orgRefNum);
		 * 
		 * getCommand.setDataBean(bean); getCommand.setContextBean(context);
		 * getCommand.performExecute();
		 * 
		 * ECSDataBean outBean = getCommand.getDataBean(); outBeans =
		 * outBean.getBeans(); }catch(Exception ex){ return null; }
		 * if(outBeans.length!=0) { try { String cmdName =
		 * ((CommandBean)outBeans
		 * [outBeans.length-1]).getCommandImplementation(); Class cmdClas =
		 * Class.forName(cmdName); ECSCmd cmdInst =
		 * (ECSCmd)(cmdClas.newInstance()); cmdInst.setContextBean(context);
		 * return cmdInst; }catch(Exception ex1){ return null; }
		 * 
		 * } else
		 */
		return null;
	}

	/**
	 * 本方法实现用户对指定功能接口的调用，它主要完成下列工作： 1.获取用户对指定功能接口的对应的类实现。 2.检查用户有没有执行该功能的权限。
	 * 3.调用该功能接口的类实现。 以前本方法的目的是提供一个工具类的方法供Task(Servlet）实现接口调用。
	 * 但是，本基础架构不推荐这样的使用方法，希望将逻辑尽量封装在逻辑业务 对象中，这样便于平台的业务构件的积累。
	 * 
	 * @param commandName
	 *            功能接口名称。
	 * @param dataBean
	 *            为功能接口调用提供的参数。
	 * @exception ECSCommandNotExistedException
	 *                指定的用户在指定的功能接口上没有对应的类实现。
	 * @exception ECSCommandExecuteException
	 *                在功能逻辑调用时产生的例外类型。
	 * @exception ECSAccessDeniedException
	 *                用户没有执行指定功能执行的权限。
	 */
	protected final ESSBean executeCommand(String commandName, ESSBean dataBean) throws ESSCommandNotExistedException, ESSCommandExecuteException, ESSAccessDeniedException {

		/*
		 * ESSCmd command = createCommand(commandName);
		 * //如果功能接口为空，则是抛出例外，提示用户错误。 if(command==null) {
		 * ESSCommandNotExistedException exception = new
		 * ESSCommandNotExistedException();
		 * exception.setCommandName(commandName);
		 * exception.setOrgName(contextBean.getOrganization().getName()); throw
		 * exception; }
		 * 
		 * if(isAllowed(commandName)){ ESSAccessDeniedException exception = new
		 * ESSAccessDeniedException(); exception.setCommandName(commandName);
		 * exception.setOrgName(contextBean.getOrganization().getName());
		 * exception.setUserName(contextBean.getUser().getName()); throw
		 * exception; }
		 * 
		 * command.setDataBean(dataBean); try { command.execute(); return
		 * command.getDataBean(); } catch(ESSCommandExecuteException exp) {
		 * throw exp; }
		 */
		return null;
	}
}
