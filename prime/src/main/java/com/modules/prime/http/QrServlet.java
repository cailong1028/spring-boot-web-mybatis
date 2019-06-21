package com.modules.prime.http;

import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;
import com.modules.prime.util.QrUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class QrServlet extends HttpServlet {
    private static final transient Logger log = LoggerFactory.getLogger(QrServlet.class);
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        //限定了url中比如包含qr/
        GZIPOutputStream zpos = null;
        String content = request.getRequestURI().split("qr/")[1];

        try {
            //response.getOutputStream().write();
            zpos = new GZIPOutputStream(response.getOutputStream());
            zpos.write(QrUtil.getQrStream(content).toByteArray());

            //response.setContentType("application/octet-stream");
            //response.setHeader("Content-Disposition", "attachment; filename=qr.png");
            response.setContentType("image/png");
            response.setStatus(200);
            response.setHeader("Content-Encoding", "gzip");

            zpos.flush();
            zpos.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(zpos != null){
                try {
                    zpos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(e);
                }
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        doGet(request, response);
    }
}
