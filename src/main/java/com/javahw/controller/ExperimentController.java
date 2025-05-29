package com.javahw.controller;

import com.javahw.entity.Experiment;
import com.javahw.service.ExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/experiment")
public class ExperimentController {
    @Autowired
    private ExperimentService experimentService;

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Experiment experiment) {
        boolean success = experimentService.addExperiment(experiment);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "添加成功" : "添加失败");
        return result;
    }

    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody Experiment experiment) {
        boolean success = experimentService.updateExperiment(experiment);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "修改成功" : "修改失败");
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        boolean success = experimentService.deleteExperiment(id);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "删除成功" : "删除失败");
        return result;
    }

    @GetMapping("/list")
    public List<Experiment> list(@RequestParam(required = false) Long teacherId) {
        return experimentService.listExperiments(teacherId);
    }

    @GetMapping("/{id}")
    public Experiment getById(@PathVariable Long id) {
        return experimentService.getExperimentById(id);
    }
} 