package com.vladhacksmile.orgmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagement
public class OrgManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrgManagementApplication.class, args);
    }

}
