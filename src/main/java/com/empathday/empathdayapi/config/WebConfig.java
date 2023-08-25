package com.empathday.empathdayapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    // TODO origin 디테일하게 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods(
                HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name(),
                HttpMethod.PUT.name(), HttpMethod.OPTIONS.name(), HttpMethod.HEAD.name()
            )
            .maxAge(3000);

        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}
