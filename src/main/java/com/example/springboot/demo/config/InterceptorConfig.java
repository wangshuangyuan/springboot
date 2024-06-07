package com.example.springboot.demo.config;

import com.example.springboot.demo.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwt())
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login", "/**/register", "/**/export", "/**/import", "/file/**","/user/**","/echarts/**","/role/**","/menu/**","/**");
    }

    @Bean
    public JwtInterceptor jwt(){
        JwtInterceptor jwtInterceptor = new JwtInterceptor();
        return  jwtInterceptor;
    }
}
