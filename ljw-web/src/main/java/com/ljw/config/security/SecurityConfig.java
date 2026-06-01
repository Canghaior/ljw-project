package com.ljw.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljw.common.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 配置。
 *
 * SecurityConfig 负责：
 * 告诉 Spring Security：哪些接口放行、哪些接口要登录、JWT 过滤器放在哪里、未登录时返回什么结果。
 *
 * <p>项目使用 JWT 做无状态认证，不使用后端 Session。
 * 登录接口放行，其他接口默认都需要携带有效 token。</p>
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ObjectMapper objectMapper;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          ObjectMapper objectMapper) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.objectMapper = objectMapper;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 前后端分离项目通常不使用 Cookie Session 维护登录态，所以关闭 CSRF。
                .csrf(AbstractHttpConfigurer::disable)

                // 关闭表单登录，避免未登录时跳转到 Spring Security 默认登录页。
                .formLogin(AbstractHttpConfigurer::disable)

                // 关闭 HTTP Basic，接口统一使用 Bearer Token。
                .httpBasic(AbstractHttpConfigurer::disable)

                // JWT 是无状态认证，服务端不创建 HttpSession。
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 配置接口访问规则：登录、错误页、静态测试页放行，其余接口需要认证。
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/error", "/favicon.ico", "/login-test.html").permitAll()
                        .anyRequest().authenticated()
                )

                // 未登录或 token 无效访问受保护接口时，返回统一 JSON，而不是默认 HTML。
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write(objectMapper.writeValueAsString(Result.fail(401, "请先登录")));
                        })
                )

                // JWT 过滤器放在用户名密码认证过滤器之前，保证业务接口进入前已经完成 token 解析。
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
