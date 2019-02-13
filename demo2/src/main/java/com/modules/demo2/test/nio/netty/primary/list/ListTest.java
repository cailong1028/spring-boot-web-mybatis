package com.modules.demo2.test.nio.netty.primary.list;

import java.util.ArrayList;
import java.util.List;

public class ListTest {


    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        list.add("a");
        list.add("b");
        list2.add("A");
        list2.add("B");

        list.addAll(list2);
        System.out.println(list.toString());
    }

}
