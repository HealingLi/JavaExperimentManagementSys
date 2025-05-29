package com.javahw.controller;

import com.javahw.entity.Grading;
import com.javahw.service.GradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grading")
public class GradingController {
    @Autowired
    private GradingService gradingService;

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Grading grading) {
        grading.setCreateTime(LocalDateTime.now());
        boolean success = gradingService.addGrading(grading);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "批改成功" : "批改失败");
        return result;
    }

    @GetMapping("/by-submission/{submissionId}")
    public List<Grading> listBySubmission(@PathVariable Long submissionId) {
        return gradingService.listGradingBySubmission(submissionId);
    }

    @GetMapping("/by-teacher/{teacherId}")
    public List<Grading> listByTeacher(@PathVariable Long teacherId) {
        return gradingService.listGradingByTeacher(teacherId);
    }
} 