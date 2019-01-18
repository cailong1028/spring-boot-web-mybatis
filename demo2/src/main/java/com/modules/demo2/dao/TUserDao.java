package com.modules.demo2.dao;

import com.modules.demo2.entity.TUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TUserDao extends CrudDao<TUserEntity> {

    TUserEntity getByUsername(@Param(value = "username") String username);

}
