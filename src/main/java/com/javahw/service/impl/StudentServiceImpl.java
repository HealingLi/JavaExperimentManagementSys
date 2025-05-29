package com.javahw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javahw.entity.Student;
import com.javahw.mapper.StudentMapper;
import com.javahw.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public boolean register(Student student) {
        // 检查用户名是否已存在
        QueryWrapper<Student> query = new QueryWrapper<>();
        query.eq("username", student.getUsername());
        if (studentMapper.selectCount(query) > 0) {
            return false;
        }
        return studentMapper.insert(student) > 0;
    }

    @Override
    public Student login(String username, String password) {
        QueryWrapper<Student> query = new QueryWrapper<>();
        query.eq("username", username).eq("password", password);
        return studentMapper.selectOne(query);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentMapper.selectById(id);
    }
} 