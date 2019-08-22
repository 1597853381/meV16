package com.qf.v16cartservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class V16CartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(V16CartServiceApplication.class, args);
    }

}
