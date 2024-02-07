package com.vladhacksmile.orgservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OrgService {
    public static void main(String[] args) {
        SpringApplication.run(OrgService.class, args);
    }
}