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
     * 本方法是一个被本框架编程人员调用来执行命令的方法。
     * @exception ESSCommandExecuteException 在调用命令时抛出的例外。
     */
    public abstract void execute() throws ESSCommandExecuteException;

    /**
     * 本方法是框架编程人员必须继承来完成指定逻辑的方法。
     * 本方法不能直接调用，而是被框架调用。
     * @exception ESSCommandExecuteException 执行过程中因为一些逻辑原因抛出的例外。
     * @exception SQLException 数据库操作例外类型。
     */
    public abstract void performExecute()throws ESSCommandExecuteException,SQLException;

    /**
     * 返回命令执行的结果。
     * @return 命令执行结果。
     */
    public abstract ESSBean getDataBean();

    /**
     * 设置命令执行所需要的参数。
     * @param databean 命令执行时所需参数。
     */
    public abstract void setDataBean(ESSBean databean);
}
