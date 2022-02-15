package com.zsq.study.service;

import com.zsq.study.model.Student;


public interface StudentService {
    Student queryStudentById(Integer id);

    void putStudentById(Integer id,String valaue);

    String getStudentById(Integer id);
}
