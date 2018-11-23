package com.cl.springbootwebmybatis.primary.array_list;

import java.util.ArrayList;
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
    }
}
