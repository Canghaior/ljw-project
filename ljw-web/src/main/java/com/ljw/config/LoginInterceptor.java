package com.ljw.config;

import com.ljw.common.exception.BizException;
import com.ljw.common.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器。
 *
 * <p>访问受保护接口前，先检查 Authorization 请求头中的 JWT token。</p>
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 请求进入 Controller 前执行。
     *
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @param handler 被访问的处理器
     * @return true 表示放行，false 表示拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        // 1. 从请求头获取 Authorization，标准格式为：Bearer token字符串。
        String authorization = request.getHeader("Authorization");

        // 2. 没有 token 或格式不正确，说明用户未登录。
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new BizException(401, "请先登录");
        }

        // 3. 去掉 Bearer 前缀，得到真正的 JWT token。
        String token = authorization.substring(7);

        try {
            // 4. 解析 token，失败时说明 token 过期、格式错误或被篡改。
            Long userId = JwtUtil.getUserId(token);

            // 5. 保存当前用户 id，后续 Controller 可以从 request 中获取。
            request.setAttribute("userId", userId);
            return true;
        } catch (Exception e) {
            throw new BizException(401, "登录已过期，请重新登录");
        }
    }
}
