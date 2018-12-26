package com.modules.demo2.dao;

import com.modules.demo2.entity.TestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestDao extends CrudDao<TestEntity> {

//    @
//    String testColumns2();

    @Select("select * from test where name = #{name2}")
    List<TestEntity> findByName(@Param("name2") String name);
}
