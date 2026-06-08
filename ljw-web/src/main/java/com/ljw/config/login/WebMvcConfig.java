package com.ljw.config.login;

import com.ljw.config.permission.PermissionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置，注册手写登录拦截器并声明公开接口。
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 需要注册到 Spring MVC 请求链中的手写登录拦截器。
     */
    private final LoginInterceptor loginInterceptor;

    /**
     * 登录成功后执行的 RBAC 权限拦截器。
     */
    private final PermissionInterceptor permissionInterceptor;

    /**
     * 创建 MVC 配置对象。
     *
     * @param loginInterceptor Spring 容器中已经创建好的登录拦截器
     * @param permissionInterceptor Spring 容器中已经创建好的权限拦截器
     */
    public WebMvcConfig(LoginInterceptor loginInterceptor,
                        PermissionInterceptor permissionInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.permissionInterceptor = permissionInterceptor;
    }

    /**
     * 注册拦截器并设置需要认证和允许匿名访问的路径。
     *
     * @param registry Spring MVC 提供的拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 先拦截所有路径，再通过 excludePathPatterns 排除公开资源。
        registry.addInterceptor(loginInterceptor)
                // order 越小越先执行，必须先确认用户身份，再进行权限检查。
                .order(0)
                .addPathPatterns("/**")
                // 登录接口必须公开，否则用户在没有 Token 时无法完成登录。
                .excludePathPatterns(
                        "/auth/login",
                        "/error",
                        "/favicon.ico",
                        "/login-test.html"
                );

        registry.addInterceptor(permissionInterceptor)
                .order(1)
                .addPathPatterns("/**")
                // 公开接口不需要执行 RBAC；其他未标注权限的接口会直接放行。
                .excludePathPatterns(
                        "/auth/login",
                        "/error",
                        "/favicon.ico",
                        "/login-test.html"
                );
    }
}
