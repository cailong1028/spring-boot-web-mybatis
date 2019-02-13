package com.modules.demo2.test.nio.netty.primary.pattern;

import org.springframework.stereotype.Component;

@Component
public class Pattern2 implements PatternInterface<String> {

    String name;

    @Override
    public void set(String s) {
        this.name = s;
    }

    @Override
    public String get() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
