package com.javahw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javahw.entity.Teacher;
import com.javahw.mapper.TeacherMapper;
import com.javahw.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public boolean register(Teacher teacher) {
        // 检查用户名是否已存在
        QueryWrapper<Teacher> query = new QueryWrapper<>();
        query.eq("username", teacher.getUsername());
        if (teacherMapper.selectCount(query) > 0) {
            return false;
        }
        return teacherMapper.insert(teacher) > 0;
    }

    @Override
    public Teacher login(String username, String password) {
        QueryWrapper<Teacher> query = new QueryWrapper<>();
        query.eq("username", username).eq("password", password);
        return teacherMapper.selectOne(query);
    }
} 