package com.ljw.config.permission;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljw.common.Result;
import com.ljw.common.permission.RequirePermission;
import com.ljw.common.security.LoginUserContext;
import com.ljw.dispatch.IPermissionDispatch;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 手写 RBAC 权限拦截器。
 *
 * <p>登录拦截器先完成身份认证，本拦截器再读取 Controller 上的
 * {@link RequirePermission} 注解，并查询数据库判断当前用户是否拥有权限。</p>
 */
@Component
public class PermissionInterceptor implements HandlerInterceptor {

    /** 权限业务编排入口。 */
    private final IPermissionDispatch permissionDispatch;

    /** 用于把 403 结果转换成 JSON。 */
    private final ObjectMapper objectMapper;

    /**
     * 创建权限拦截器。
     *
     * @param permissionDispatch 权限业务编排对象
     * @param objectMapper JSON 序列化工具
     */
    public PermissionInterceptor(IPermissionDispatch permissionDispatch, ObjectMapper objectMapper) {
        this.permissionDispatch = permissionDispatch;
        this.objectMapper = objectMapper;
    }

    /**
     * Controller 执行前检查自定义权限注解。
     *
     * @param request 当前 HTTP 请求
     * @param response 当前 HTTP 响应
     * @param handler 即将执行的处理器
     * @return true 表示有权限或接口未声明权限；false 表示返回 403
     * @throws Exception 写入 JSON 响应失败时抛出
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 静态资源等处理器不是 Controller 方法，不参与方法注解权限检查。
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        RequirePermission requirePermission = findRequirePermission(handlerMethod);
        if (requirePermission == null) {
            return true;
        }

        Long userId = LoginUserContext.getUserId();
        String permissionCode = requirePermission.value();
        if (permissionDispatch.hasPermission(userId, permissionCode)) {
            return true;
        }

        writeForbidden(response, "没有权限：" + permissionCode);
        return false;
    }

    /**
     * 查找接口要求的权限。
     *
     * <p>优先读取方法上的注解；方法未声明时，再读取 Controller 类上的注解。</p>
     *
     * @param handlerMethod 当前 Controller 方法
     * @return 找到的权限注解；没有声明权限时返回 null
     */
    private RequirePermission findRequirePermission(HandlerMethod handlerMethod) {
        RequirePermission methodAnnotation =
                handlerMethod.getMethodAnnotation(RequirePermission.class);
        if (methodAnnotation != null) {
            return methodAnnotation;
        }
        return handlerMethod.getBeanType().getAnnotation(RequirePermission.class);
    }

    /**
     * 权限不足时返回统一的 HTTP 403 JSON。
     *
     * @param response 当前 HTTP 响应
     * @param message 权限不足提示
     * @throws Exception JSON 序列化或响应写入失败时抛出
     */
    private void writeForbidden(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Result.fail(403, message)));
    }
}
