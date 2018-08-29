package com.cl.springbootwebmybatis.primary.pattern;

public interface PatternInterface<T> {
    void set(T t);
    T get();
}
