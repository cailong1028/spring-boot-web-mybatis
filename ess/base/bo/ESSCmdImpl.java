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
     * 在调用前用于存储调用参数，在调用后存储执行结果。
     */
    protected ESSBean dataBean = null; 
    
    /**
     * 构造函数。
     */
    public ESSCmdImpl() {
    }

    /**
     * 本方法是一个被本框架编程人员调用来执行命令的方法。
     * @exception ESSCommandExecuteException 在进行一般的逻辑处理时发生的例外类型。
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
     * 本方法是框架编程人员必须继承来完成指定逻辑的方法。
     * 本方法不能直接调用，而是被框架调用。
     * @exception ESSCommandExecuteException 执行过程中抛出的例外。
     * @exception SQLException 数据库操作例外。
     */
    public abstract void performExecute() throws ESSCommandExecuteException,SQLException;

    /**
     * 返回命令执行的结果。
     * @return 命令执行结果。
     */
    public ESSBean getDataBean() {
        return this.dataBean;
    }

    /**
     * 设置命令执行所需要的参数。
     * @param databean 命令执行时所需参数。
     */
    public void setDataBean(ESSBean dataBean) {
        this.dataBean = dataBean;
    }
}
