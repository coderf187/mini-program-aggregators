package com.gene;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.gene","com.gene.*" })
public class MiniProgramServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniProgramServerApplication.class, args);
    }
}
