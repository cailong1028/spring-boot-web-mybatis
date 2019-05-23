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
 * ҵ���߼�����࣬�ṩ��ҵ���߼�����Ļ����ӿ�
 */
public class ESSBO extends ESSObject {
	/**
	 * ���캯��,�������Ĺ��캯���У���ò�Ҫ����������Ϊ���������� ���������㡣
	 */
	public ESSBO() {
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
		ESSPL pl = new ESSPL();
		return pl.retrieve(sql, po, pageSize, page);
	}

	/**
	 * ��ѯָ�������Ķ��󼯺�
	 * 
	 * @param po
	 *            ��ѯ�Ķ����ṩ��ѯ�û���SQL���
	 * @param whereClause
	 *            ��ѯ����
	 * @param page
	 *            ��ѯҳ��,0Ϊ����ҳ
	 * @return ess.base.util.Result ��ѯ���
	 * @see #retrieve(ESSPLQueryInterface, String, boolean, String, int)
	 */
	public ess.base.util.Result retrieve(ESSPLQueryInterface po, String whereClause, int page) throws java.sql.SQLException {
		return retrieve(po, whereClause, true, null, page);
	}

	/**
	 * ��ѯָ�������Ķ��󼯺�
	 * 
	 * @param po
	 *            ��ѯ�Ķ����ṩ��ѯ�û���SQL���
	 * @param whereClause
	 *            ��ѯ����
	 * @param orderBy
	 *            ��������������"ORDER BY"�ؼ���
	 * @param page
	 *            ��ѯҳ��,0Ϊ����ҳ
	 * @return ess.base.util.Result ��ѯ���
	 * @see #retrieve(ESSPLQueryInterface,String,boolean,String,int)
	 */
	public ess.base.util.Result retrieve(ESSPLQueryInterface po, String whereClause, String orderBy, int page) throws java.sql.SQLException {
		return retrieve(po, whereClause, true, orderBy, page);
	}

	/**
	 * ��ѯָ�������Ķ��󼯺�, ��Bean���Ϸ�ʽ���أ��������ҳ
	 * 
	 * @param po
	 *            ��ѯ�Ķ����ṩ��ѯ�û���SQL���
	 * @param whereClause
	 *            ��ѯ����
	 * @param orderBy
	 *            ��������������"ORDER BY"�ؼ���
	 * @return ess.base.bo.ESSBean ��ѯ���,û��ʱ���ؿ�
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
	 * ��ѯָ�������Ķ��󼯺�
	 * 
	 * @param po
	 *            ��ѯ�Ķ����ṩ��ѯ�û���SQL���
	 * @param whereClause
	 *            ��ѯ����
	 * @param orderBy
	 *            ��������������"ORDER BY"�ؼ���
	 * @param appendWhere
	 *            �Ƿ��"WHERE"�ؼ��֣�true,��WHERE�ؼ��֣����� ��"AND"�ؼ���
	 * @param page
	 *            ��ѯҳ��,0Ϊ����ҳ
	 * @return ess.base.util.Result ��ѯ���
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
	 * ִ�����ݸ��²���������SQLֱ���ɲ���ָ����
	 */
	public int executeUpdate(String sql) throws SQLException, ESSConnectionGetTimeOutException {
		// ��������
		int row = 0;
		java.sql.Statement statement = null;
		java.sql.Connection conn = null;
		try {
			if (session == null)
				conn = getDataStore().getConnection();
			else
				conn = session;
			statement = conn.createStatement();
			// ����ɾ��������
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
	 * ��������һ�����ɼ�����������Ǹ��ݵ�ǰ�û���ʶ�ͽӿ����ƣ������ݿ��� ��ѯ�ӿڵľ���ʵ�֡�
	 */
	protected final ESSCmd createCommand(String commandName) {

		/*
		 * ECSDataBean[] outBeans = null; try { GetCommandCmd getCommand = null;
		 * //�����ܽӿ�Ϊʵ�ֹ��ܽӿڲ�ѯ�Ļ����࣬�����ٴ�ͨ�����ݿ��ѯ�� getCommand = new
		 * GetCommandCmdImpl(); //��ѯ���������Ǳ�����Bean�еġ� CommandBean bean = new
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
	 * ������ʵ���û���ָ�����ܽӿڵĵ��ã�����Ҫ������й����� 1.��ȡ�û���ָ�����ܽӿڵĶ�Ӧ����ʵ�֡� 2.����û���û��ִ�иù��ܵ�Ȩ�ޡ�
	 * 3.���øù��ܽӿڵ���ʵ�֡� ��ǰ��������Ŀ�����ṩһ��������ķ�����Task(Servlet��ʵ�ֽӿڵ��á�
	 * ���ǣ��������ܹ����Ƽ�������ʹ�÷�����ϣ�����߼�������װ���߼�ҵ�� �����У���������ƽ̨��ҵ�񹹼��Ļ��ۡ�
	 * 
	 * @param commandName
	 *            ���ܽӿ����ơ�
	 * @param dataBean
	 *            Ϊ���ܽӿڵ����ṩ�Ĳ�����
	 * @exception ECSCommandNotExistedException
	 *                ָ�����û���ָ���Ĺ��ܽӿ���û�ж�Ӧ����ʵ�֡�
	 * @exception ECSCommandExecuteException
	 *                �ڹ����߼�����ʱ�������������͡�
	 * @exception ECSAccessDeniedException
	 *                �û�û��ִ��ָ������ִ�е�Ȩ�ޡ�
	 */
	protected final ESSBean executeCommand(String commandName, ESSBean dataBean) throws ESSCommandNotExistedException, ESSCommandExecuteException, ESSAccessDeniedException {

		/*
		 * ESSCmd command = createCommand(commandName);
		 * //������ܽӿ�Ϊ�գ������׳����⣬��ʾ�û����� if(command==null) {
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
