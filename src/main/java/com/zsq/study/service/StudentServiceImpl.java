package com.zsq.study.service;

import com.zsq.study.mapper.StudentMapper;
import com.zsq.study.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl  implements StudentService{

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public Student queryStudentById(Integer id){
        return studentMapper.selectByPrimaryKey(id);
    }

    @Override
    public void putStudentById(Integer id,String value) {
        redisTemplate.opsForValue().set(id,value);

    }

    @Override
    public String getStudentById(Integer id) {
        return (String) redisTemplate.opsForValue().get(id);
    }
}
