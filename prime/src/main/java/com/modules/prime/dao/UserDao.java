package com.modules.prime.dao;

import com.modules.prime.annotation.Dao;
import com.modules.prime.annotation.Sql;

import java.util.HashMap;
import java.util.List;

@Dao
public interface UserDao{

    @Sql("select name, email, code from user where name = ? limit 2;")
    public List<HashMap<String, Object>> getUser(String name);
}
