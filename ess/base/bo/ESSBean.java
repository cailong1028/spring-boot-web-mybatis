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
 * 本类为业务对象类的基础类，实现了业务对象的列表封装。
 */
public class ESSBean extends ESSObject implements Serializable{

    /**
     * 当前类型为LIST时，存储多个实例。
     */
    protected Vector list = null;

    /**
     * 构造函数。
     */
    public ESSBean() {
    }

    /**
     * 将值加入到列表中。
     * @param bean　加入到列表中的参数。
     */
    public void addBeanToList(ESSBean bean){
        if(bean == null) return;
        if(list==null) list = new Vector();

        list.addElement(bean);
    }
    
    /**
     * CL 111116 添加 用以替代DataBean
     * 将值加入到列表中。
     * @param bean　加入到列表中的参数。
     */
    public void addObjectToList(Object obj){
        if(obj == null) return;
        if(list==null) list = new Vector();

        list.addElement(obj);
    }

    /**
     * 将值列表加入到列表中
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
     * 返回列表中所有的值。
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
     * 从列表中删除指定的对象。
     * @param bean　要删除的对象。
     */
    public boolean removeBean(ESSBean bean){
        if(list==null) 
            return false;

        return list.removeElement(bean);
    }

    /**
     * 从列表中删除指定位置的对象。
     * @param index　要删除的对象的位置。
     */
    public void removeBeanAt(int index){
        if(list==null) 
            return;

        list.removeElementAt(index);
    }

    /**
     * 列表中数据个数。
     */
    public int size(){
        if(list==null)
            return 1;

        return list.size();
    }
    
    /**
     * 从列表中取指定位置的数据。本方法相对于size()方法，
     * 列表大小由size()获取。当列表为空时，size()等于1，
     * 本方法返回本对象。
     * 
     * @param index 位置索引。
     * @see #size()
     */
    public ESSBean getBean(int index){
        if(list == null){
            return this;
        }

        return (ESSBean)list.elementAt(index);
    }

    /**
     * CL 111116 添加getObject方法  
     * 此方法不同于getBean方法，获取list中的对象，方便Querylet执行查询后数据,不用手动类型转换到DataBean
     * 这样Querylet的retrieve方法就可以替代BO的retrieve方法（因为bo的retrieve方法必须对应的po对象，需要重载getQuerySql方法）
     * 在Querylet的retrieve方法中使用ESSBean替代DataBean
     * 
     * 使用bo.retrieve好处是ESSBean对象控制，但麻烦
     * 
     * @param index 位置索引。
     * @see #size()
     */
    public Object getObjectFromList(int index) throws Exception {
        if(list == null){
        	throw new Exception("指定数据不存在");
        }

        return list.elementAt(index);
    }
    
    /**
     * 将列表中的数据放入指定数组中并返回。
     * @param objs 结果数组，必须已分配好
     * @return 结果数组
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
     * 清空数据列表
     */
    public void clearBeans(){
        list = null;
    }
}
