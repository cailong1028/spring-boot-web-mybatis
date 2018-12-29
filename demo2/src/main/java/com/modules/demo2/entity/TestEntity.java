package com.modules.demo2.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TestEntity extends DataEntity<TestEntity> {

    private int id;
    private String name;
    private String email;
    private Date updateDate;

}
