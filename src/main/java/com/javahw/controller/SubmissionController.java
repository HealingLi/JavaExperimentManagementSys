package com.javahw.controller;

import com.javahw.entity.Submission;
import com.javahw.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/submission")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Submission submission) {
        System.out.println("Received Submission (after deserialization): " + submission);
        System.out.println("Received codeZip (after deserialization): " + submission.getCodeZip());
        System.out.println("Received resultImg (after deserialization): " + submission.getResultImg());
        submission.setCreateTime(LocalDateTime.now());
        boolean success = submissionService.addSubmission(submission);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "提交成功" : "提交失败");
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id, @RequestParam Long studentId) {
        boolean success = submissionService.deleteSubmission(id, studentId);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "删除成功" : "删除失败");
        return result;
    }

    @GetMapping("/my")
    public List<Submission> listMy(@RequestParam Long studentId) {
        return submissionService.listMySubmissions(studentId);
    }

    @GetMapping("/by-experiment/{experimentId}")
    public List<Submission> listByExperiment(@PathVariable Long experimentId) {
        return submissionService.listSubmissionsByExperiment(experimentId);
    }

    @GetMapping("/{id}")
    public Submission getById(@PathVariable Long id) {
        return submissionService.getSubmissionById(id);
    }

    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody Submission submission) {
        Map<String, Object> result = new HashMap<>();
        // TODO: 在这里加入逻辑检查 submission 是否属于当前登录学生，防止修改他人作业
        boolean success = submissionService.updateSubmission(submission);
        result.put("success", success);
        result.put("message", success ? "更新成功" : "更新失败");
        return result;
    }
} 