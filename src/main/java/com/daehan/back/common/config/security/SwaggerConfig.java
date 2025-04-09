package com.daehan.back.common.config.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@OpenAPIDefinition(
        info = @Info(title = "User-Service API 명세서",
                description = "사용자 어플 서비스 API 명세서",
                version = "v1"))
@Configuration
public class SwaggerConfig {
    // Swagger 관련 설정을 여기에 추가할 수 있습니다.
    // 예를 들어, Swagger UI의 경로, 보안 설정 등을 추가할 수 있습니다.
    // 현재는 기본 설정만 사용하고 있습니다.
    // Swagger UI는 기본적으로 /swagger-ui/index.html 경로에서 접근할 수 있습니다.
    // 추가적인 설정이 필요하다면, SwaggerConfig 클래스에 메서드를 추가하여 설정할 수 있습니다.
    @Bean
    public OpenAPI openAPI(){
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Collections.singletonList(securityRequirement));
    }
}
