package com.zsq.study;

import com.zsq.study.service.StudentService;
import com.zsq.study.service.StudentServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@MapperScan(basePackages="com.zsq.study.mapper")
public class StudyApplication implements CommandLineRunner {
    @Autowired
    StudentService StudentService;

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext=  SpringApplication.run(StudyApplication.class, args);
        System.out.println(14&25);


    }

    @Override
    public void run(String... args) throws Exception {

    }
}
