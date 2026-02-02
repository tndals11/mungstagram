package com.example.mungstragram._common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // upload로 시작하는 모든 요청을 가로챈다
        registry.addResourceHandler("/upload/**")
                // 실제 하드디스크 경로와 연결
                .addResourceLocations("file:///C:/mungstagram/upload/");
    }
}
