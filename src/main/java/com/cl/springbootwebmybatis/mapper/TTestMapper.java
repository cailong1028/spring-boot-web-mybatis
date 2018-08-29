package com.cl.springbootwebmybatis.mapper;

import com.cl.springbootwebmybatis.domain.TTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TTestMapper {

    @Select("select * from t_test where name = #{name}")
    TTest findByName(@Param("name") String name);

}
