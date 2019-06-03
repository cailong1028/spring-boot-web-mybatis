package com.modules.prime.dao;

import com.modules.prime.annotation.Sql;

import java.util.HashMap;
import java.util.List;

public interface LoginDao extends PrimeDao {
    @Sql(value = "select * from test where name = ? limit 2;")
    List<HashMap<String, Object>> login(String name);
}
