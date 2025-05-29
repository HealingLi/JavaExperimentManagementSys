package com.javahw.controller;

import com.javahw.entity.Student;
import com.javahw.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Student student) {
        Map<String, Object> result = new HashMap<>();
        student.setCreateTime(LocalDateTime.now());
        boolean success = studentService.register(student);
        result.put("success", success);
        result.put("message", success ? "注册成功" : "用户名已存在");
        return result;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginForm) {
        String username = loginForm.get("username");
        String password = loginForm.get("password");
        Student student = studentService.login(username, password);
        Map<String, Object> result = new HashMap<>();
        if (student != null) {
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("student", student);
        } else {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
        }
        return result;
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }
} 