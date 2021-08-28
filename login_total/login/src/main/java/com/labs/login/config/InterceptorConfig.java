package com.labs.login.config;

import com.labs.login.component.AuthInterceptor;
import com.labs.login.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private AuthService authService;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(new AuthInterceptor(authService))
                .addPathPatterns("/api/test/**")
                .addPathPatterns("/api/auth/logout")
                .addPathPatterns("/api/user/sign-out")
                .excludePathPatterns("/api/auth/login")
                .excludePathPatterns("/api/user/sign-up");
    }
}
