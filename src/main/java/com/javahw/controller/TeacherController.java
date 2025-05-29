package com.javahw.controller;

import com.javahw.entity.Teacher;
import com.javahw.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Teacher teacher) {
        Map<String, Object> result = new HashMap<>();
        teacher.setCreateTime(LocalDateTime.now());
        boolean success = teacherService.register(teacher);
        result.put("success", success);
        result.put("message", success ? "注册成功" : "用户名已存在");
        return result;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginForm) {
        String username = loginForm.get("username");
        String password = loginForm.get("password");
        Teacher teacher = teacherService.login(username, password);
        Map<String, Object> result = new HashMap<>();
        if (teacher != null) {
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("teacher", teacher);
        } else {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
        }
        return result;
    }
} 