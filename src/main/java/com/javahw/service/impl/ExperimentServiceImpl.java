package com.javahw.service.impl;

import com.javahw.entity.Experiment;
import com.javahw.mapper.ExperimentMapper;
import com.javahw.service.ExperimentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperimentServiceImpl implements ExperimentService {
    @Autowired
    private ExperimentMapper experimentMapper;

    @Override
    public boolean addExperiment(Experiment experiment) {
        return experimentMapper.insert(experiment) > 0;
    }

    @Override
    public boolean updateExperiment(Experiment experiment) {
        return experimentMapper.updateById(experiment) > 0;
    }

    @Override
    public boolean deleteExperiment(Long id) {
        return experimentMapper.deleteById(id) > 0;
    }

    @Override
    public List<Experiment> listExperiments(Long teacherId) {
        QueryWrapper<Experiment> queryWrapper = new QueryWrapper<>();
        if (teacherId != null) {
            queryWrapper.eq("teacher_id", teacherId);
        }
        return experimentMapper.selectList(queryWrapper);
    }

    @Override
    public Experiment getExperimentById(Long id) {
        return experimentMapper.selectById(id);
    }
} 