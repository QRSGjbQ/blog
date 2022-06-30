package com.gjb.blog.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截过滤所有以admin开头的访问
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                //排除登录接口的访问
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
