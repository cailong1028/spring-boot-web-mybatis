package com.modules.demo2.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TUserEntity implements Serializable {
    private int id;
    private String userName;
    private String password;
}
