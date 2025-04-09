package com.example.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.system.jdbc.dao")
public class TrainingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainingSystemApplication.class, args);
    }
}
