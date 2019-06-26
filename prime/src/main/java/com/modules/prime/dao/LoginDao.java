package com.modules.prime.dao;

import com.modules.prime.annotation.Dao;
import com.modules.prime.annotation.Sql;

import java.util.HashMap;
import java.util.List;

@Dao
public interface LoginDao extends PrimeDao {
    @Sql("select * from test where name = ? limit 2;")
    List<HashMap<String, Object>> login(String name);
}
