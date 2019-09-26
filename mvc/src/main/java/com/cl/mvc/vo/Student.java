package com.cl.mvc.vo;

import java.io.Serializable;
import java.util.List;

public class Student implements Serializable {
    private String name;
    private String age;
    private Teacher teacher;
    private List<Course> courseList;
    private boolean honer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public boolean isHoner() {
        return honer;
    }

    public void setHoner(boolean honer) {
        this.honer = honer;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(name + "\t");
        sb.append(age + "\t");
        sb.append(teacher.getName() + "\t");
        sb.append(teacher.getGender() + "\t");
        if(courseList != null){
            for(Course c:courseList){
                sb.append(c.getName() + "\t");
                sb.append(c.getPeriod() + "\t");
            }
        }
        sb.append(isHoner());
        return sb.toString();
    }
}
