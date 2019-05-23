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
 * 简单数据容器类，用于保存数据集合
 */
public class DataBean extends ESSBean {
    /**
     * DataBean 构造子注解。
     */
    public DataBean() {
        super();
    }

    /**
     * 将值加入到列表中。
     * @param bean　加入到列表中的参数。
     */
    public void addObject(Object bean){
        if(list==null) list = new Vector();
        list.addElement(bean);
    }
    
    /**
     * 从列表中取指定位置的数据。
     * @param index 位置索引。
     */
    public Object getObject(int index) throws Exception{
        if(list==null) 
            throw new Exception("指定数据不存在");

        if(index <0 || index > list.size()){
            return null;
        }
        
        return list.elementAt(index);
    }

    /**
     * 列表中数据个数。
     */
    public int size(){
        if(list==null)
            return 0;

        return list.size();
    }

    /**
     * 判断指定对象是否在列表中
     * @param obj 查找对象
     * @return boolean true, 存在，false, 不存在
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
