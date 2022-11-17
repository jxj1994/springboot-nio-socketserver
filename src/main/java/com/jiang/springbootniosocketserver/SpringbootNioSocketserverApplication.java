package com.jiang.springbootniosocketserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootNioSocketserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootNioSocketserverApplication.class, args);
    }

}
