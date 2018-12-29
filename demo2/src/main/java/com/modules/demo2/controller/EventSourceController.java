package com.modules.demo2.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@Controller
@RequestMapping(value = "/es")
public class EventSourceController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
//    @ResponseBody
    public HttpServletResponse index(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        EsObject esObject = new EsObject();
//        esObject.setName("cl");
//        response.setContentType("text/event-stream");
//        response.setCharacterEncoding("UTF-8");
//        return esObject;
        response.setCharacterEncoding("UTF-8");
        Writer writer = response.getWriter();
        writer.write("{\"user\": 1}");
        writer.close();
        return response;
    }

    @GetMapping(value = "/getEs")
    public HttpServletResponse getEsObject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
//        response.
        Writer writer = response.getWriter();
        writer.write("{\"data\": \"esObject name\"} \n\n");
//        writer.close();
        return response;
    }

}

@Getter
@Setter
class EsObject{
    private String name;
}
