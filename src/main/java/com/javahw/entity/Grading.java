package com.javahw.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("grading")
public class Grading {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long submissionId;
    private Long teacherId;
    private String grade;
    private String comment;
    private LocalDateTime createTime;
} 