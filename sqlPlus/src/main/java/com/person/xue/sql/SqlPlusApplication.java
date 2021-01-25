package com.person.xue.sql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SqlPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlPlusApplication.class, args);
    }

}
