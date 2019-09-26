package com.cl.mvc.vo;

import java.io.Serializable;

public class Grade implements Serializable {
    private String headMater;
    private String name;

    public String getHeadMater() {
        return headMater;
    }

    public void setHeadMater(String headMater) {
        this.headMater = headMater;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return getName() + "\t" + getHeadMater();
    }
}
