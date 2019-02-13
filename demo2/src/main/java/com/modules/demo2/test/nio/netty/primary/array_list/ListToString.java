package com.modules.demo2.test.nio.netty.primary.array_list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListToString {

    public static void main(String[] args){


        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        String str = String.join(",", list);
        System.out.println(str);

        List<String> list2 = new ArrayList<>();
        list.forEach(one -> {
            list2.add(one + "_2");
        });
        String str2 = String.join("、", list2);
        System.out.println(str2);

        String str3 = ",,";
        List<String> list3 = Arrays.asList(str3.replaceAll("\\s*", "").split(","));
        System.out.println(list3.size());
        for(String one: list3){
            System.out.println(one);
        }

        System.out.println(0 - 100L);
        String str4 = null;
        say(str4);
    }

    public static void say(String str){
        System.out.println(str);
        A a = new A();
        A a2 = new A();
        System.out.println(a.getA().equals(a2.getA()));
    }
}

class A{
    public String getA(){
        return "a";
    }
}