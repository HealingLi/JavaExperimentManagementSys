package com.javahw.service;

import com.javahw.entity.Student;

public interface StudentService {
    boolean register(Student student);
    Student login(String username, String password);
    Student getStudentById(Long id);
} 