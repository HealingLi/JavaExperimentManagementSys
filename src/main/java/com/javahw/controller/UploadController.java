package com.javahw.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Value("${upload.dir:uploads}")
    private String uploadDir;

    private String saveFile(MultipartFile file, String subDir, String[] allowedTypes) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String ext = StringUtils.getFilenameExtension(originalFilename);
        boolean allowed = false;
        for (String type : allowedTypes) {
            if (type.equalsIgnoreCase(ext)) {
                allowed = true;
                break;
            }
        }
        if (!allowed) {
            throw new IOException("文件类型不允许");
        }
        String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String dirPath = uploadDir + File.separator + subDir + File.separator + datePath;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String newName = UUID.randomUUID().toString().replace("-", "") + "." + ext;
        File dest = new File(dir, newName);
        file.transferTo(dest);
        return "/uploads/" + subDir + "/" + datePath + "/" + newName;
    }

    @PostMapping("/code")
    public Map<String, Object> uploadCode(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        try {
            String path = saveFile(file, "code", new String[]{"zip"});
            result.put("success", true);
            result.put("path", path);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PostMapping("/result")
    public Map<String, Object> uploadResult(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        try {
            String path = saveFile(file, "result", new String[]{"jpg", "jpeg", "png", "gif"});
            result.put("success", true);
            result.put("path", path);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
} 