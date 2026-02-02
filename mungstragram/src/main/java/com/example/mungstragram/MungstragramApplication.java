package com.example.mungstragram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @EnableJpaAuditing: JPA에서 엔티티의 생성/수정 시간을 자동으로 추적
 */
@SpringBootApplication
@EnableJpaAuditing
public class MungstragramApplication {

    public static void main(String[] args) {
        SpringApplication.run(MungstragramApplication.class, args);
    }

}
