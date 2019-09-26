package com.cl.mvc.controller;

import com.cl.mvc.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/form")
public class FormController {

    @RequestMapping("")
    public String formPage(String id, Model model){
        Student student = new Student();
        Grade grade = new Grade();
        if(id != null && id != ""){
            Teacher teacher = new Teacher();
            teacher.setName("张老师");
            teacher.setGender("男");

            List<Course> courses = new ArrayList<>();
            Course course1 = new Course();
            Course course2 = new Course();
            course1.setName("数学");
            course2.setName("语文");
            course1.setPeriod("8学时");
            course2.setPeriod("16学时");
            courses.add(course1);
            courses.add(course2);

            student.setName("武平");
            student.setAge("12");
            student.setTeacher(teacher);
            student.setCourseList(courses);

            grade.setHeadMater("李老师");
            grade.setName("三年级四班");

        }
        model.addAttribute("student", student);
        model.addAttribute("grade", grade);
        return "form";
    }

//    @RequestMapping(value = "/save", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    @RequestMapping(value = "/save")
    public String save(@ModelAttribute(value = "student") Student student, @ModelAttribute(value = "grade") Grade grade, Model model){
        System.out.println(student.toString());
        System.out.println(grade.toString());
        //如果不该动页面，输出的如下，注意 jsp中的
        //武平1,三年级四班	12	张老师	男	数学	8学时	语文	16学时
        //武平1,三年级四班	李老师
        return null;
    }

    @RequestMapping(value = "getFamilyInfo", method = RequestMethod.GET, produces = "application/json;charset=utf-8;")
    @ResponseBody
    public Object getFamilyInfo(@RequestParam String name){
        Person papa = new Person();
        papa.setName("蔡老爹");
        papa.setRelation("父子");
        papa.setRelationName(name);
        return papa;
    }
}
