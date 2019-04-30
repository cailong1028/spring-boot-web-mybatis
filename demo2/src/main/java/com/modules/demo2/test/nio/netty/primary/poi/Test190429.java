package com.modules.demo2.test.nio.netty.primary.poi;

import java.util.regex.Pattern;

public class Test190429 {

    public static void main(String[] args) {
        boolean isMatch = Pattern.matches("\\d+.\\d+%", "02.3%");
        System.out.println("match " + isMatch);
    }
}
