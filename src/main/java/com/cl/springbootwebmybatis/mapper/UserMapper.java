package com.cl.springbootwebmybatis.mapper;

import com.cl.springbootwebmybatis.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where name = #{name}")
    User findByName(@Param("name") String name);
}
