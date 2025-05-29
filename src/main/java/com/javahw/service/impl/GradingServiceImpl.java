package com.javahw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javahw.entity.Grading;
import com.javahw.mapper.GradingMapper;
import com.javahw.service.GradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradingServiceImpl implements GradingService {
    @Autowired
    private GradingMapper gradingMapper;

    @Override
    public boolean addGrading(Grading grading) {
        return gradingMapper.insert(grading) > 0;
    }

    @Override
    public List<Grading> listGradingBySubmission(Long submissionId) {
        QueryWrapper<Grading> query = new QueryWrapper<>();
        query.eq("submission_id", submissionId);
        return gradingMapper.selectList(query);
    }

    @Override
    public List<Grading> listGradingByTeacher(Long teacherId) {
        QueryWrapper<Grading> query = new QueryWrapper<>();
        query.eq("teacher_id", teacherId);
        return gradingMapper.selectList(query);
    }
} 