package com.ljw.config.permission;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljw.common.permission.RequirePermission;
import com.ljw.common.security.LoginSession;
import com.ljw.common.security.LoginUser;
import com.ljw.common.security.LoginUserContext;
import com.ljw.dispatch.IPermissionDispatch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 手写 RBAC 权限拦截器测试。
 */
class PermissionInterceptorTest {

    /**
     * 每个测试结束后清理 ThreadLocal，避免测试线程复用造成数据相互影响。
     */
    @AfterEach
    void clearLoginContext() {
        LoginUserContext.clear();
    }

    /**
     * 验证用户拥有接口要求的权限时，请求可以继续执行。
     */
    @Test
    void shouldAllowRequestWhenUserHasPermission() throws Exception {
        IPermissionDispatch permissionDispatch = mock(IPermissionDispatch.class);
        PermissionInterceptor interceptor =
                new PermissionInterceptor(permissionDispatch, new ObjectMapper());
        HandlerMethod handlerMethod = createHandlerMethod("protectedMethod");
        setCurrentLoginUser(1L);
        when(permissionDispatch.hasPermission(1L, "user:list")).thenReturn(true);

        boolean allowed = interceptor.preHandle(
                new MockHttpServletRequest(),
                new MockHttpServletResponse(),
                handlerMethod
        );

        assertTrue(allowed);
        verify(permissionDispatch).hasPermission(1L, "user:list");
    }

    /**
     * 验证用户没有接口要求的权限时，拦截器返回 HTTP 403 和统一 JSON。
     */
    @Test
    void shouldReturnForbiddenWhenUserHasNoPermission() throws Exception {
        IPermissionDispatch permissionDispatch = mock(IPermissionDispatch.class);
        PermissionInterceptor interceptor =
                new PermissionInterceptor(permissionDispatch, new ObjectMapper());
        HandlerMethod handlerMethod = createHandlerMethod("protectedMethod");
        setCurrentLoginUser(2L);
        when(permissionDispatch.hasPermission(2L, "user:list")).thenReturn(false);
        MockHttpServletResponse response = new MockHttpServletResponse();

        boolean allowed = interceptor.preHandle(
                new MockHttpServletRequest(),
                response,
                handlerMethod
        );

        assertEquals(false, allowed);
        assertEquals(403, response.getStatus());
        assertTrue(response.getContentAsString().contains("\"code\":403"));
        assertTrue(response.getContentAsString().contains("user:list"));
    }

    /**
     * 验证没有声明 RequirePermission 的接口只检查登录，不执行 RBAC 查询。
     */
    @Test
    void shouldSkipPermissionCheckWhenMethodHasNoAnnotation() throws Exception {
        IPermissionDispatch permissionDispatch = mock(IPermissionDispatch.class);
        PermissionInterceptor interceptor =
                new PermissionInterceptor(permissionDispatch, new ObjectMapper());
        HandlerMethod handlerMethod = createHandlerMethod("publicMethod");

        boolean allowed = interceptor.preHandle(
                new MockHttpServletRequest(),
                new MockHttpServletResponse(),
                handlerMethod
        );

        assertTrue(allowed);
        verify(permissionDispatch, never()).hasPermission(1L, "user:list");
    }

    /**
     * 构造一个模拟的 Controller 方法，供拦截器读取方法注解。
     *
     * @param methodName 测试 Controller 中的方法名
     * @return Spring MVC HandlerMethod
     */
    private HandlerMethod createHandlerMethod(String methodName) throws NoSuchMethodException {
        TestController controller = new TestController();
        return new HandlerMethod(controller, TestController.class.getMethod(methodName));
    }

    /**
     * 把测试用户放入手写登录上下文，模拟登录拦截器已经认证成功。
     *
     * @param userId 测试用户 id
     */
    private void setCurrentLoginUser(Long userId) {
        LoginUser loginUser = new LoginUser(userId, "test-user", "测试用户");
        LoginSession session = new LoginSession(
                "test-token",
                loginUser,
                LocalDateTime.now().plusHours(1)
        );
        LoginUserContext.setSession(session);
    }

    /**
     * 仅供权限拦截器单元测试使用的模拟 Controller。
     */
    static class TestController {

        /** 模拟需要 user:list 权限的接口。 */
        @RequirePermission("user:list")
        public void protectedMethod() {
        }

        /** 模拟只要求登录、不要求业务权限的接口。 */
        public void publicMethod() {
        }
    }
}
