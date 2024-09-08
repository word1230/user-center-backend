package com.cheems.behind;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cheems.behind.mapper")
public class BehindApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehindApplication.class, args);
    }

}
