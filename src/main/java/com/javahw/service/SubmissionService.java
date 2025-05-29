package com.javahw.service;

import com.javahw.entity.Submission;
import java.util.List;

public interface SubmissionService {
    boolean addSubmission(Submission submission);
    boolean deleteSubmission(Long id, Long studentId);
    List<Submission> listMySubmissions(Long studentId);
    List<Submission> listSubmissionsByExperiment(Long experimentId);
    boolean updateSubmission(Submission submission);
    Submission getSubmissionById(Long id);
} 