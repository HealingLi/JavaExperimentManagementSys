package com.javahw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.javahw.mapper")
public class JavaHwApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaHwApplication.class, args);
    }
} 