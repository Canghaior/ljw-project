package com.ljw.config.security;

import com.ljw.common.security.LoginUser;
import com.ljw.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT 认证过滤器。
 *负责：
 * 从请求头里解析 JWT，并把用户信息放进 SecurityContextHolder。
 *
 * <p>它负责在请求进入 Controller 之前解析 Authorization 请求头，
 * 并把当前登录用户保存到 Spring Security 的 SecurityContext 中。
 * 这样后续 Controller、Dispatch、Service 都不需要依赖 HttpServletRequest。</p>
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        // 没有 token 时不在过滤器里直接报错，交给 Spring Security 判断当前接口是否必须登录。
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);

        try {
            Claims claims = jwtUtil.parseToken(token);

            Long userId = Long.valueOf(claims.getSubject());
            String username = claims.get("username", String.class);
            String nickname = claims.get("nickname", String.class);

            LoginUser loginUser = new LoginUser(userId, username, nickname);

            // 当前项目暂时没有角色权限，authorities 先使用空集合。
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(loginUser, null, Collections.emptyList());

            // 把已认证用户放入安全上下文，当前请求后续代码都可以通过 SecurityUserContext 获取。
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            // token 过期、格式错误、签名错误时，清理上下文，避免错误身份继续向后传递。
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
