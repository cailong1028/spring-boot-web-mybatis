package com.cl.mvc.vo;

import java.io.Serializable;

public class Course implements Serializable {
    private String name;
    private String period;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
