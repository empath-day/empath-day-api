package com.empathday.empathdayapi.common.apidoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
            .title("하루공감 프로젝트 API Document")
            .version("v0.0.1")
            .description("하루공감 프로젝트의 API 명세서입니다.");
        return new OpenAPI()
            .components(new Components())
            .info(info);
    }
}
