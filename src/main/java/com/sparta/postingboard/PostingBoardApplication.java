package com.sparta.postingboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing    //JPAAuditing 을 사용한다는 것을 알려줌
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PostingBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostingBoardApplication.class, args);
    }

}
