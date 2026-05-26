package com.ljw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置类。
 *
 * <p>主要用于注册登录拦截器，并配置哪些路径不需要登录即可访问。</p>
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    public WebMvcConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    /**
     * 注册登录拦截器。
     *
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                // 默认拦截所有请求，后续接口默认都需要登录。
                .addPathPatterns("/**")
                // 登录接口必须放行，否则用户无法获取 token。
                .excludePathPatterns("/auth/login")
                // 登录测试页面放行。
                .excludePathPatterns("/login-test.html")
                // 浏览器自动请求的小图标放行。
                .excludePathPatterns("/favicon.ico")
                // Spring Boot 默认错误路径放行，避免错误处理再次被拦截。
                .excludePathPatterns("/error");
    }
}
