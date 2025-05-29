package com.javahw.service;

import com.javahw.entity.Teacher;

public interface TeacherService {
    boolean register(Teacher teacher);
    Teacher login(String username, String password);
} 