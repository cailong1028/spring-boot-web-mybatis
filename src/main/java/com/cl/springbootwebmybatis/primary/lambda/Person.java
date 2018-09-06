package com.cl.springbootwebmybatis.primary.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Person {

    public String name;
    public int age;

    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args){
        List<Person> list = new ArrayList<Person>() {
            {
                add(new Person("cl", 1));
                add(new Person("cl2", 2));
                add(new Person("cl3", 3));
                add(new Person("cl4", 4));
                add(new Person("cl5", 5));
            }
        };
        //List<Person> list2 = Arrays.asList(list.toArray());
        list.forEach(Person::accept);
        list.forEach(one -> {
            System.out.println("lambda --> "+one.name);
        });
        list.stream().filter(one -> one.age > 2).limit(2).forEach(one -> System.out.println(one.name));
    }

    private static void accept(Person one) {
        System.out.println(one.name);
    }
}
