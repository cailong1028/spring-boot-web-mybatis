/******************************************************************************
    File DataBean.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003
    all rights reserved
******************************************************************************/
package ess.base.bo;

import java.util.Vector;

/**
 * �����������࣬���ڱ������ݼ���
 */
public class DataBean extends ESSBean {
    /**
     * DataBean ������ע�⡣
     */
    public DataBean() {
        super();
    }

    /**
     * ��ֵ���뵽�б��С�
     * @param bean�����뵽�б��еĲ�����
     */
    public void addObject(Object bean){
        if(list==null) list = new Vector();
        list.addElement(bean);
    }
    
    /**
     * ���б���ȡָ��λ�õ����ݡ�
     * @param index λ��������
     */
    public Object getObject(int index) throws Exception{
        if(list==null) 
            throw new Exception("ָ�����ݲ�����");

        if(index <0 || index > list.size()){
            return null;
        }
        
        return list.elementAt(index);
    }

    /**
     * �б������ݸ�����
     */
    public int size(){
        if(list==null)
            return 0;

        return list.size();
    }

    /**
     * �ж�ָ�������Ƿ����б���
     * @param obj ���Ҷ���
     * @return boolean true, ���ڣ�false, ������
     */
    public boolean checkExist(Object obj){
        if(size() <1 || obj == null){
            return false;
        }
        
        boolean exist = false;
        for(int i=0; i<size(); i++){
            if(obj.equals(list.elementAt(i))){
                exist = true;
                break;
            }
        }
        
        return exist;
    }
}
