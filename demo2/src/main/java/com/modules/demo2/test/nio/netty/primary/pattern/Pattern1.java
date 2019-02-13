package com.modules.demo2.test.nio.netty.primary.pattern;

import org.springframework.stereotype.Component;

@Component
public class Pattern1 implements PatternInterface<Integer> {

    private Integer name;

    @Override
    public void set(Integer i) {
        this.name = i;
    }

    @Override
    public Integer get() {
        return this.name;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }
}
