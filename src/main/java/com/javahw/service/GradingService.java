package com.javahw.service;

import com.javahw.entity.Grading;
import java.util.List;

public interface GradingService {
    boolean addGrading(Grading grading);
    List<Grading> listGradingBySubmission(Long submissionId);
    List<Grading> listGradingByTeacher(Long teacherId);
} 