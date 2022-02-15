package com.zsq.study.web;

import com.zsq.study.model.Student;
import com.zsq.study.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;

@RestController
public class StudentController {


    @Autowired
    StudentService studentService;

    @RequestMapping(value="/student")
    public Student student(Integer id){
        return studentService.queryStudentById(1);
    }

    @GetMapping(value="/put")
    public String putStudent(Integer id,String name){
        studentService.putStudentById(id,name);
        return "OK";
    }

    @GetMapping(value="/get")
    public String putStudent(Integer id){
        return studentService.getStudentById(id);
    }

}
