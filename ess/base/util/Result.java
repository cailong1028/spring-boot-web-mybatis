/******************************************************************************
    File: Result.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/
package ess.base.util;

import ess.base.bo.ESSBean;

/**
 * 该类为用于保存查询结果的工具类，主要用于在BO层对象与JSP page之间
 * 传递结果,多用于涉及数据库查询的业务。
 */
public class Result {
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 保存结果Bean列表。
     */
    protected ESSBean beans = null;

    /**
     * 计数器，用于保存查询结果集的记录个数。
     */
    protected int counter = 0;

    /**
     * 查询返回的结果中记录的起始位置。
     */
    protected int startPosition = 0;

    /**
     * 查询返回的结果中记录的个数。
     */
    protected int resultSize = 20;

    /**
     * ResultHolder 构造子注解。
     */
    public Result() {
        super();
    }

    /**
     * ResultHolder 构造子注解。
     */
    public Result(int start, int resultSize) {
        super();

        this.startPosition = start;
        this.resultSize = resultSize;
    }

    /**
     * 获取结果列表。
     * 
     * @return com.ecs.base.bo.ECSBean[] 结果Beans.
     */
    public ESSBean getBeans() {
        return beans;
    }

    /**
     * 获取计数器值，返回结果集个数。
     *
     * @return int 结果集个数
     * @see #setCounter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * 获取记录结果的当前页序号。
     *
     * @return int
     */
    public int getCurrentPageNo() {
        if(resultSize <= 0)
            return 1;
        else
            return 1+(startPosition + resultSize - 1)/resultSize;
    }

    /**
     * 获取记录结果的最大页数。
     *
     * @return int
     */
    public int getMaxPageNo() {
        if(resultSize <= 0){
            return 1;
        }

        return (counter + resultSize - 1)/resultSize;
    }

    /**
     * 获取当前设置的结果长度。
     *
     * @return int
     */
    public int getResultSize() {
        return resultSize;
    }

    /**
     * 获取结果中记录的起始位置。
     *
     * @return int
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * 根据指定的页号，获取结果中记录的起始位置。
     * 页号为1，2，3，...
     * 每页的记录个数由属性resultSize指定
     *
     * @return int 指定页的起始记录位置
     * @see #setResultSize(int)
     */
    public int getStartPosition(int pageNo) {
        if(pageNo <1)
            return 0;

        int start = resultSize*(pageNo - 1);
        this.startPosition = start;
        
        return start;
    }

    /**
     * 根据指定的页号和每页的记录个数，获取结果中记录的起始位置。
     * 页号为1，2，3，...
     *
     * @return int
     */
    public static int getStartPosition(int pageNo,int resultSize) {
        return resultSize*(pageNo - 1);
    }

    /**
     * 设置结果Bean列表。
     *
     * @param newBeas com.ecs.base.bo.ECSBean[] 结果Beans
     */
    public void setBeans(ESSBean newBeans) {
        beans = newBeans;
    }

    /**
     * 设置结果集计数数值，指结果集总长度
     *
     * @param newCounter int
     */
    public void setCounter(int newCounter) {
        counter = newCounter;
    }

    /**
     * 设置结果集长度。
     *
     * @param newResultSize int
     */
    public void setResultSize(int newResultSize) {
        resultSize = newResultSize;
    }

    /**
     * 设置结果集的起始记录位置。
     *
     * @param newStartPosition int
     */
    public void setStartPosition(int newStartPosition) {
        startPosition = newStartPosition;
    }

    /**
     * 获取结果列表。
     * 
     * @return com.ecs.base.bo.ECSBean[] 结果Beans.
     */
    public ESSBean[] getAllBeans() {
        if(beans == null)
            return null;
        else
            return beans.getBeans();
    }

    /**
     * 获取当前页的结果集长度。
     *
     * @return int
     */   
    public int getPageSize() {
        if(beans == null){
            return 0;
        }else{
            return beans.getBeans().length;
        }
    }
    
    /**
     * 检查指定对象是否在列表中
     * @param checkBean 被检查bean
     * @return boolean 是否存在，true存在，false,不存在
     */
    public boolean exist(ESSBean checkBean){
        boolean existed = false;
        
        if(beans == null || checkBean == null){
            return false;
        }

        ESSBean[] beansList = beans.getBeans();
        for(int i=0; i<beansList.length; i++){
            if(beansList[i].equals(checkBean)){
                existed = true;
                break;
            }
        }
        
        return existed;
    }
}
