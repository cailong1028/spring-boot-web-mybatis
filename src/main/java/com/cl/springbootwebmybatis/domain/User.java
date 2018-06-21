package com.cl.springbootwebmybatis.domain;

import java.io.Serializable;

public class User implements Serializable{
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

