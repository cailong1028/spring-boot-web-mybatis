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
 * ����Ϊ���ڱ����ѯ����Ĺ����࣬��Ҫ������BO�������JSP page֮��
 * ���ݽ��,�������漰���ݿ��ѯ��ҵ��
 */
public class Result {
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * ������Bean�б�
     */
    protected ESSBean beans = null;

    /**
     * �����������ڱ����ѯ������ļ�¼������
     */
    protected int counter = 0;

    /**
     * ��ѯ���صĽ���м�¼����ʼλ�á�
     */
    protected int startPosition = 0;

    /**
     * ��ѯ���صĽ���м�¼�ĸ�����
     */
    protected int resultSize = 20;

    /**
     * ResultHolder ������ע�⡣
     */
    public Result() {
        super();
    }

    /**
     * ResultHolder ������ע�⡣
     */
    public Result(int start, int resultSize) {
        super();

        this.startPosition = start;
        this.resultSize = resultSize;
    }

    /**
     * ��ȡ����б�
     * 
     * @return com.ecs.base.bo.ECSBean[] ���Beans.
     */
    public ESSBean getBeans() {
        return beans;
    }

    /**
     * ��ȡ������ֵ�����ؽ����������
     *
     * @return int ���������
     * @see #setCounter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * ��ȡ��¼����ĵ�ǰҳ��š�
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
     * ��ȡ��¼��������ҳ����
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
     * ��ȡ��ǰ���õĽ�����ȡ�
     *
     * @return int
     */
    public int getResultSize() {
        return resultSize;
    }

    /**
     * ��ȡ����м�¼����ʼλ�á�
     *
     * @return int
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * ����ָ����ҳ�ţ���ȡ����м�¼����ʼλ�á�
     * ҳ��Ϊ1��2��3��...
     * ÿҳ�ļ�¼����������resultSizeָ��
     *
     * @return int ָ��ҳ����ʼ��¼λ��
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
     * ����ָ����ҳ�ź�ÿҳ�ļ�¼��������ȡ����м�¼����ʼλ�á�
     * ҳ��Ϊ1��2��3��...
     *
     * @return int
     */
    public static int getStartPosition(int pageNo,int resultSize) {
        return resultSize*(pageNo - 1);
    }

    /**
     * ���ý��Bean�б�
     *
     * @param newBeas com.ecs.base.bo.ECSBean[] ���Beans
     */
    public void setBeans(ESSBean newBeans) {
        beans = newBeans;
    }

    /**
     * ���ý����������ֵ��ָ������ܳ���
     *
     * @param newCounter int
     */
    public void setCounter(int newCounter) {
        counter = newCounter;
    }

    /**
     * ���ý�������ȡ�
     *
     * @param newResultSize int
     */
    public void setResultSize(int newResultSize) {
        resultSize = newResultSize;
    }

    /**
     * ���ý��������ʼ��¼λ�á�
     *
     * @param newStartPosition int
     */
    public void setStartPosition(int newStartPosition) {
        startPosition = newStartPosition;
    }

    /**
     * ��ȡ����б�
     * 
     * @return com.ecs.base.bo.ECSBean[] ���Beans.
     */
    public ESSBean[] getAllBeans() {
        if(beans == null)
            return null;
        else
            return beans.getBeans();
    }

    /**
     * ��ȡ��ǰҳ�Ľ�������ȡ�
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
     * ���ָ�������Ƿ����б���
     * @param checkBean �����bean
     * @return boolean �Ƿ���ڣ�true���ڣ�false,������
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
