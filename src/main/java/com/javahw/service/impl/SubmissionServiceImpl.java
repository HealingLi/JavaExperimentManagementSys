package com.javahw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javahw.entity.Submission;
import com.javahw.mapper.SubmissionMapper;
import com.javahw.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    @Autowired
    private SubmissionMapper submissionMapper;

    @Override
    public boolean addSubmission(Submission submission) {
        System.out.println("Before insert - SubmissionServiceImpl - resultImg: " + submission.getResultImg());
        System.out.println("Before insert - SubmissionServiceImpl - codeZip: " + submission.getCodeZip());
        return submissionMapper.insert(submission) > 0;
    }

    @Override
    public boolean deleteSubmission(Long id, Long studentId) {
        QueryWrapper<Submission> query = new QueryWrapper<>();
        query.eq("id", id).eq("student_id", studentId);
        return submissionMapper.delete(query) > 0;
    }

    @Override
    public List<Submission> listMySubmissions(Long studentId) {
        QueryWrapper<Submission> query = new QueryWrapper<>();
        query.eq("student_id", studentId);
        return submissionMapper.selectList(query);
    }

    @Override
    public List<Submission> listSubmissionsByExperiment(Long experimentId) {
        QueryWrapper<Submission> query = new QueryWrapper<>();
        query.eq("experiment_id", experimentId);
        return submissionMapper.selectList(query);
    }

    @Override
    public boolean updateSubmission(Submission submission) {
        return submissionMapper.updateById(submission) > 0;
    }

    @Override
    public Submission getSubmissionById(Long id) {
        return submissionMapper.selectById(id);
    }
} 