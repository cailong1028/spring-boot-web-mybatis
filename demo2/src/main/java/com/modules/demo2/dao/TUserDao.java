package com.modules.demo2.dao;

import com.modules.demo2.entity.TUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TUserDao extends CrudDao<TUserEntity> {

    TUserEntity getByUsername(@Param(value = "username") String username);

    int batchInsert(List<TUserEntity> tUserList);
}
