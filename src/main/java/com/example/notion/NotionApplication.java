package com.example.notion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan  // 이 부분 추가
public class NotionApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotionApplication.class, args);
    }

}