package com.cl.springbootwebmybatis.primary;

public interface BeanInterface<T> {

    void setBeanName(String beanName);

    void sayBean();

    BeanInterface getBean();

}
