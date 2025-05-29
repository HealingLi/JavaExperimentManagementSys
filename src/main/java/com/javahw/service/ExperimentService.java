package com.javahw.service;

import com.javahw.entity.Experiment;
import java.util.List;

public interface ExperimentService {
    boolean addExperiment(Experiment experiment);
    boolean updateExperiment(Experiment experiment);
    boolean deleteExperiment(Long id);
    List<Experiment> listExperiments(Long teacherId);
    Experiment getExperimentById(Long id);
} 