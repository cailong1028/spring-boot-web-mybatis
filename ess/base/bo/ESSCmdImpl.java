/******************************************************************************
    File ESSCmdImpl.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003
    all rights reserved
******************************************************************************/
package ess.base.bo;

import java.sql.SQLException;

import ess.base.exp.ESSCommandExecuteException;

public abstract class ESSCmdImpl extends ESSObject implements ESSCmd {

    /**
     * �ڵ���ǰ���ڴ洢���ò������ڵ��ú�洢ִ�н����
     */
    protected ESSBean dataBean = null; 
    
    /**
     * ���캯����
     */
    public ESSCmdImpl() {
    }

    /**
     * ��������һ��������ܱ����Ա������ִ������ķ�����
     * @exception ESSCommandExecuteException �ڽ���һ����߼�����ʱ�������������͡�
     */
    public void execute() throws ESSCommandExecuteException{
        try {
            performExecute();
        }catch(SQLException ex){
            ESSCommandExecuteException exception= new ESSCommandExecuteException();
            exception.setCommandName(getClass().getName());
            exception.setException(ex);
            throw exception;
        }
    }

    /**
     * �������ǿ�ܱ����Ա����̳������ָ���߼��ķ�����
     * ����������ֱ�ӵ��ã����Ǳ���ܵ��á�
     * @exception ESSCommandExecuteException ִ�й������׳������⡣
     * @exception SQLException ���ݿ�������⡣
     */
    public abstract void performExecute() throws ESSCommandExecuteException,SQLException;

    /**
     * ��������ִ�еĽ����
     * @return ����ִ�н����
     */
    public ESSBean getDataBean() {
        return this.dataBean;
    }

    /**
     * ��������ִ������Ҫ�Ĳ�����
     * @param databean ����ִ��ʱ���������
     */
    public void setDataBean(ESSBean dataBean) {
        this.dataBean = dataBean;
    }
}
