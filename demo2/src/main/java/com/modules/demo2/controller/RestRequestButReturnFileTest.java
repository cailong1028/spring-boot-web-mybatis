package com.modules.demo2.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/restFile")
public class RestRequestButReturnFileTest {

    //form调用下载文件
    //ajax调用返回blob
    //详见index.html调用
    @RequestMapping(value = "/file", consumes = {MediaType.ALL_VALUE})
    public void producesTest(@RequestParam(value = "name", required = false) String name,
                             HttpServletRequest request, HttpServletResponse response){

        String age = request.getParameter("age");

        System.out.println("/restFile/file param name-->"+name);
        System.out.println("/restFile/file param age-->"+age);
        String a = "/Users/cl/bqj/xxx.xml";
        System.out.println("aaa-->"+a.substring(a.lastIndexOf("/") + 1, a.length()));

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=excel.zip");
//        response
        try {
            byte[] testBytes = readAsByteArray(new FileInputStream(new File("/Users/bqj/Desktop/logo.png.zip")));
            response.getOutputStream().write(testBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/html", consumes = {MediaType.ALL_VALUE})
    public void htmlTest(@RequestParam(value = "name", required = false) String name,
                             HttpServletRequest request, HttpServletResponse response){

        String htmlStr = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <script src=\"https://cdn.bootcss.com/jquery/1.12.1/jquery.min.js\"></script>\n" +
                "    <script src=\"https://cdn.bootcss.com/jquery.qrcode/1.0/jquery.qrcode.min.js\"></script>\n" +
                "    <title>index page</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div id=\"qrcode\"/>\n" +
                "</body>\n" +
                "<script>\n" +
                "    $(() => {\n" +
                "        $('#qrcode').qrcode(\"https://mp.weixin.qq.com/s?src=11&timestamp=1556163046&ver=1567&signature=JAHEzJNhQQnFBgbBS0S-RQg5iQyGLiIqzQJs0aUba5c7vLdGBjAchpod14yOR5YXRXcik9G5dWwsb128DO0*11Gug7nDcNI6W04BkXAjisLDsk4RmsgzMfJnjO0-AyU1&new=1\");\n" +
                "    });\n" +
                "</script>\n" +
                "</html>";

        response.setContentType("text/html");
        //response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        response.setStatus(200);
        try {
            response.getOutputStream().write(htmlStr.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static final byte[] readAsByteArray(InputStream is) throws Exception {
        List<byte[]> all = new ArrayList();
        int count = 0;
        byte[] catchs = new byte[65536];

        byte[] allDatas;
        try {
            int read ;
            try {
                while((read = is.read(catchs)) > -1) {
                    count += read;
                    allDatas = new byte[read];
                    System.arraycopy(catchs, 0, allDatas, 0, read);
                    all.add(allDatas);
                }
            } catch (IOException var11) {
                ;
            }
        } finally {
            is.close();
        }

        allDatas = new byte[count];
        int copyCounts = 0;

        byte[] b;
        for(Iterator var8 = all.iterator(); var8.hasNext(); copyCounts += b.length) {
            b = (byte[])var8.next();
            System.arraycopy(b, 0, allDatas, copyCounts, b.length);
        }

        return allDatas;
    }
}
