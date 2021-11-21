package com.oldeighthome.heavennote;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.oldeighthome.heavennote.mapper")
public class HeavennoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeavennoteApplication.class, args);
    }

}
