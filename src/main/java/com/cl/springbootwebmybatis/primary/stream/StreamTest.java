package com.cl.springbootwebmybatis.primary.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    public static void main(String[] args){

        List<Student> students = new ArrayList<>();

        //java.util.Random，设置seed之后每次的随机值是相同的
        Random random = new Random(50);
        for(int i = 0; i < 10; i++){
            students.add(new Student(String.valueOf(i), random.nextInt(100)));
        }

        //stream.collect
        List<Student> studentList = students.stream()
                .filter(one -> one.getScore() > 50)
                .sorted(Comparator.comparing(Student::getScore).reversed())
                //.map(Student::toString)
                .map(Student::_map)
                .collect(Collectors.toList());
        System.out.println(studentList);

        //每个stream的聚合、消费或收集只能执行一次，同时测试forEach
        students.stream()
                .filter(one -> one.getScore() > 50)
                .sorted(Comparator.comparing(Student::getScore).reversed())
                //.map(Student::toString)
                .map(Student::_map).forEach(System.out::println);

        //测试map
        studentList.stream().map(Student::toString);

        //Stream.generate
        Stream<String> stream = Stream.generate(() -> "user").limit(10);
        stream.map(String::valueOf).collect(Collectors.toList());

        //Stream.of 以及 stream.forEach
        Stream.of(1,2,3).forEach(System.out::println);

        //Stream的延迟执行
        String[] arr = {"aa", "bb", "cc"};
        //Arrays.stream <==> Stream.of
        //Arrays.stream(arr);
        Stream<String> stream_2 = Stream.of(arr);
        //stream_2已经执行了一次map，此时stream_2已经不能再执行聚合消费和收集，但是返回一个新的stream，赋值给stream_2_2
        Stream<String> stream_2_2 = stream_2.map(one -> {
            System.out.println("delay to do map");
            return one + "++";
        });
        System.out.println("execute me first");
        List<String> list2 = stream_2_2.collect(Collectors.toList());
        System.out.println("execute me last");


    }

}

class Student{
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString(){
        return getName() + "-" + getScore();
    }

    public Student _map(){
        System.out.println("start _map");
        Student s = new Student("", 0);
        s.setName("a");
        s.setScore(60);
        return s;
    }
}