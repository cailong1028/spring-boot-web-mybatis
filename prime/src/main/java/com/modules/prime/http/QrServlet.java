package com.modules.prime.http;

import com.modules.prime.util.QrUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QrServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        //限定了url中比如包含qr/
        String content = request.getRequestURI().split("qr/")[1];
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=qr.png");
        response.setStatus(200);
        try {
            response.getOutputStream().write(QrUtil.getQrStream(content).toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        doGet(request, response);
    }
}
