package com.sparta.postingboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PostingBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostingBoardApplication.class, args);
    }

}
