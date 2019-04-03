package com.modules.demo2.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CrudDao<T> extends BaseDao{
    public T get(@Param("id") String id);
    public T get(T entity);
    public List<T> findList(T entity);
    public int insert(T entity);
    public int update(T entity);
    public int delete(String id);
    public int delete(T entity);
}
