package com.example.wbsystem_ssm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.wbsystem_ssm.dao")
//扫描别的包内的结构
@ServletComponentScan(basePackages = "com.example.wbsystem_ssm,com.example.wbsystem_ssm.security")
public class WbSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(WbSystemApplication.class, args);
    }
}
