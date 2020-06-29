package com.erayt.xfunds.sql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class XfundsSqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(XfundsSqlApplication.class, args);
    }

}
