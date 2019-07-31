package com.modules.prime.test.t190731;

import java.io.File;
import java.util.regex.Matcher;

/**
 * Created by JXL on 2019/7/31.
 */
public class Test {
    public static void main(String[] args) {
        String a = "com.a.b";
        String b = a.replaceAll("\\.", Matcher.quoteReplacement(File.separator));
        System.out.println(b);
    }
}
