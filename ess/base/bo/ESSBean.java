/******************************************************************************
    File ESSBean.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003
    all rights reserved
******************************************************************************/
package ess.base.bo;

import java.io.Serializable;
import java.util.Vector;

/**
 * ����Ϊҵ�������Ļ����࣬ʵ����ҵ�������б��װ��
 */
public class ESSBean extends ESSObject implements Serializable{

    /**
     * ��ǰ����ΪLISTʱ���洢���ʵ����
     */
    protected Vector list = null;

    /**
     * ���캯����
     */
    public ESSBean() {
    }

    /**
     * ��ֵ���뵽�б��С�
     * @param bean�����뵽�б��еĲ�����
     */
    public void addBeanToList(ESSBean bean){
        if(bean == null) return;
        if(list==null) list = new Vector();

        list.addElement(bean);
    }
    
    /**
     * CL 111116 ��� �������DataBean
     * ��ֵ���뵽�б��С�
     * @param bean�����뵽�б��еĲ�����
     */
    public void addObjectToList(Object obj){
        if(obj == null) return;
        if(list==null) list = new Vector();

        list.addElement(obj);
    }

    /**
     * ��ֵ�б���뵽�б���
     * @param beans
     */
    public void addBeansToList(ESSBean[] beans){
        if(beans == null) return;
        if(list==null) list = new Vector();

        for(int i=0; i<beans.length; i++){
            list.addElement(beans[i]);
        }
    }

    /**
     * �����б������е�ֵ��
     */
    public ESSBean[] getBeans(){
        ESSBean[] beans = null;
        if(list!=null) {
            beans = new ESSBean[list.size()];
            list.copyInto(beans);
        }else {
            beans = new ESSBean[1];
            beans[0] = this;
        }
        return beans;
    }

    /**
     * ���б���ɾ��ָ���Ķ���
     * @param bean��Ҫɾ���Ķ���
     */
    public boolean removeBean(ESSBean bean){
        if(list==null) 
            return false;

        return list.removeElement(bean);
    }

    /**
     * ���б���ɾ��ָ��λ�õĶ���
     * @param index��Ҫɾ���Ķ����λ�á�
     */
    public void removeBeanAt(int index){
        if(list==null) 
            return;

        list.removeElementAt(index);
    }

    /**
     * �б������ݸ�����
     */
    public int size(){
        if(list==null)
            return 1;

        return list.size();
    }
    
    /**
     * ���б���ȡָ��λ�õ����ݡ������������size()������
     * �б��С��size()��ȡ�����б�Ϊ��ʱ��size()����1��
     * ���������ر�����
     * 
     * @param index λ��������
     * @see #size()
     */
    public ESSBean getBean(int index){
        if(list == null){
            return this;
        }

        return (ESSBean)list.elementAt(index);
    }

    /**
     * CL 111116 ���getObject����  
     * �˷�����ͬ��getBean��������ȡlist�еĶ��󣬷���Queryletִ�в�ѯ������,�����ֶ�����ת����DataBean
     * ����Querylet��retrieve�����Ϳ������BO��retrieve��������Ϊbo��retrieve���������Ӧ��po������Ҫ����getQuerySql������
     * ��Querylet��retrieve������ʹ��ESSBean���DataBean
     * 
     * ʹ��bo.retrieve�ô���ESSBean������ƣ����鷳
     * 
     * @param index λ��������
     * @see #size()
     */
    public Object getObjectFromList(int index) throws Exception {
        if(list == null){
        	throw new Exception("ָ�����ݲ�����");
        }

        return list.elementAt(index);
    }
    
    /**
     * ���б��е����ݷ���ָ�������в����ء�
     * @param objs ������飬�����ѷ����
     * @return �������
     */
    public Object[] getBeans(Object[] objs){
        if(list!=null) {
            list.copyInto(objs);
        }else {
            objs[0] = this;
        }
        return objs;
    }

    /**
     * ��������б�
     */
    public void clearBeans(){
        list = null;
    }
}
