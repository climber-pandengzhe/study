package com.zsq.study.mapper;

import com.zsq.study.model.Student;

public interface StudentMapper {
    int deleteByPrimaryKey(Integer lid);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer lid);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}