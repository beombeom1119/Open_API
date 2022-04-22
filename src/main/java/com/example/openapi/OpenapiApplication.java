package com.example.openapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OpenapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenapiApplication.class, args);
    }
    //https://api.covid19api.com/total/dayone/country/kr 코로나 관련 open API
}
