package com.chl.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chl.article.mapper")
public class UniArticleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniArticleApiApplication.class, args);
    }

}
