package com.ljw.config.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljw.common.Result;
import com.ljw.common.security.LoginSession;
import com.ljw.common.security.LoginSessionManager;
import com.ljw.common.security.LoginUserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 手写登录认证拦截器。
 *
 * <p>没有使用 Spring Security 或 JJWT 的认证能力，而是基于 Spring MVC
 * 的 HandlerInterceptor 扩展点，手动完成请求头读取、Token 校验和用户上下文设置。</p>
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * Authorization 请求头中 Token 前面的固定认证方案。
     *
     * <p>完整格式为 {@code Authorization: Bearer xxxxx}。</p>
     */
    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * 登录会话管理器，用于根据 Token 查询服务端保存的用户会话。
     */
    private final LoginSessionManager loginSessionManager;

    /**
     * Spring 提供的 JSON 序列化工具。
     *
     * <p>认证失败时，使用它把统一 Result 对象转换成 JSON 响应。</p>
     */
    private final ObjectMapper objectMapper;

    /**
     * 创建登录认证拦截器。
     *
     * <p>两个参数都由 Spring 容器通过构造器自动注入。</p>
     *
     * @param loginSessionManager 手写登录会话管理器
     * @param objectMapper JSON 序列化工具
     */
    public LoginInterceptor(LoginSessionManager loginSessionManager, ObjectMapper objectMapper) {
        this.loginSessionManager = loginSessionManager;
        this.objectMapper = objectMapper;
    }

    /**
     * Controller 方法执行前进行登录认证。
     *
     * <p>返回 true 表示认证通过，请求可以继续；返回 false 表示已写入 401 响应，
     * Spring MVC 不再调用目标 Controller。</p>
     *
     * @param request 当前 HTTP 请求，用于读取 Authorization 请求头
     * @param response 当前 HTTP 响应，认证失败时写入 401 JSON
     * @param handler 即将执行的 Controller 方法或其他处理器
     * @return true 表示放行，false 表示拦截请求
     * @throws Exception 写入响应发生异常时向上抛出
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 请求线程可能被容器复用，认证前先清理可能残留的旧上下文。
        LoginUserContext.clear();

        // 读取客户端按照约定放在 Authorization 请求头中的登录凭证。
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            writeUnauthorized(response, "请先登录");
            return false;
        }

        // 去掉 Bearer 前缀后得到真正的随机 Token，再到服务端会话存储中查询。
        String token = authorization.substring(BEARER_PREFIX.length()).trim();
        LoginSession session = loginSessionManager.findValidSession(token);
        if (session == null) {
            writeUnauthorized(response, "登录已过期，请重新登录");
            return false;
        }

        // Token 校验通过后保存当前会话，后续业务代码可以从 LoginUserContext 获取用户。
        LoginUserContext.setSession(session);
        return true;
    }

    /**
     * 整个请求完成后清理登录上下文。
     *
     * <p>该方法在 Controller 正常返回或抛出异常后都会执行。</p>
     *
     * @param request 当前 HTTP 请求
     * @param response 当前 HTTP 响应
     * @param handler 已执行的 Controller 方法或其他处理器
     * @param ex 请求执行期间出现的异常，没有异常时为 null
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        // 无论接口执行成功还是抛出异常，都必须清理 ThreadLocal。
        LoginUserContext.clear();
    }

    /**
     * 未登录时直接返回统一 JSON，并使用真实的 HTTP 401 状态码。
     *
     * @param response 当前 HTTP 响应
     * @param message 返回给客户端的认证失败提示
     * @throws Exception JSON 序列化或响应写入失败时抛出
     */
    private void writeUnauthorized(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Result.fail(401, message)));
    }
}
