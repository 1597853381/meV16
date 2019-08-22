package com.qf.v16home;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class V16HomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(V16HomeApplication.class, args);
    }

}
