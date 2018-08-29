package com.cl.springbootwebmybatis.bean;

public class UserBean{

    private String userName;
    private String password;

    public UserBean(String name, String pwd){
        this.userName = name;
        this.password = pwd;
    }

    public void sayName(){
        System.out.println("name is: " + this.userName);
    }

    public void start(){
        System.out.println(this.getClass().getName() + " start");
    }

    public void destroy(){
        System.out.println(this.getClass().getName() + " destroy");
    }
}
