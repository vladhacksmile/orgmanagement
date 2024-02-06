package com.vladhacksmile.orgmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan
@EnableTransactionManagement
public class OrgManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrgManagementApplication.class, args);
    }

}
