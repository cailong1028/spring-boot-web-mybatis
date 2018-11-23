package com.modules.demo2.mapper;

import com.modules.demo2.mapper.domain.Test;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestMapper {

    @Select("select * from test where name = #{name}")
    Test getTestByName(@Param("name") String name);

    @Insert("insert into test() values()")
    Void addTest(Test test);
}
