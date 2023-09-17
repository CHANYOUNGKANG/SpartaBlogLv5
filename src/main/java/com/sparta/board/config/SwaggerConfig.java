package com.sparta.board.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "블로그 API 명세서",
                description = "블로그 서비스 API 명세서",
                version = "v3"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

//    @Bean
//    public GroupedOpenApi chatOpenApi() {
//        String[] paths = {"/v3/**"};//v1로 시작하는 모든 경로를 chatOpenApi로 묶음
//
//        return GroupedOpenApi.builder()
//                .group("채팅서비스 API v3")
//                .pathsToMatch(paths)
//                .build();
//    }
}