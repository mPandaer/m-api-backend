package com.pandaer.server.modules.apiinfo.config;

import com.pandaer.server.modules.apiinfo.interceptor.ThirdApiInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private ThirdApiInterceptor thirdApiInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(thirdApiInterceptor).addPathPatterns("/api/**");
    }
}
