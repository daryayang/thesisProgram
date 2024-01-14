package com.thesis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@SpringBootApplication

@MapperScan("com.thesis.*.mapper")
public class thesisApplication {
    public static void main(String[] args) {
        SpringApplication.run(thesisApplication.class, args);
    }


}
