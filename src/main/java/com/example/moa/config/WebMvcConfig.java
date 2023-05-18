package com.example.moa.config;

import com.example.moa.jwt.JwtTokenInterceptor;
import com.example.moa.web.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private final JwtTokenInterceptor jwtTokenInterceptor;

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(jwtTokenInterceptor);
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/user/**");

        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/recruit/**")
                .addPathPatterns("/user/ingredient/register")
                .addPathPatterns("/chat/list")
    }
}

