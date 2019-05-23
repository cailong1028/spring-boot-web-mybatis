/******************************************************************************
    File ESSCmd.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003
    all rights reserved
******************************************************************************/
package ess.base.bo;

import ess.base.exp.ESSCommandExecuteException;
import java.sql.SQLException;

public interface ESSCmd {
    /**
     * ��������һ��������ܱ����Ա������ִ������ķ�����
     * @exception ESSCommandExecuteException �ڵ�������ʱ�׳������⡣
     */
    public abstract void execute() throws ESSCommandExecuteException;

    /**
     * �������ǿ�ܱ����Ա����̳������ָ���߼��ķ�����
     * ����������ֱ�ӵ��ã����Ǳ���ܵ��á�
     * @exception ESSCommandExecuteException ִ�й�������ΪһЩ�߼�ԭ���׳������⡣
     * @exception SQLException ���ݿ�����������͡�
     */
    public abstract void performExecute()throws ESSCommandExecuteException,SQLException;

    /**
     * ��������ִ�еĽ����
     * @return ����ִ�н����
     */
    public abstract ESSBean getDataBean();

    /**
     * ��������ִ������Ҫ�Ĳ�����
     * @param databean ����ִ��ʱ���������
     */
    public abstract void setDataBean(ESSBean databean);
}
