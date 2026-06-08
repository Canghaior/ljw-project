package com.ljw.common.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 验证手写登录会话最关键的创建、查询和退出流程。
 */
class LoginSessionManagerTest {

    /**
     * 验证登录成功后能够创建 Token，并通过 Token 找回同一个用户会话。
     */
    @Test
    void shouldCreateAndFindLoginSession() {
        LoginSessionManager manager = new LoginSessionManager(24);
        LoginUser loginUser = new LoginUser(1L, "admin", "管理员");

        String token = manager.createSession(loginUser);
        LoginSession session = manager.findValidSession(token);

        assertNotNull(token);
        assertNotNull(session);
        assertEquals(1L, session.getLoginUser().getUserId());
    }

    /**
     * 验证退出登录删除会话后，原 Token 不能再次查询到登录状态。
     */
    @Test
    void shouldRemoveSessionWhenUserLogsOut() {
        LoginSessionManager manager = new LoginSessionManager(24);
        String token = manager.createSession(new LoginUser(1L, "admin", "管理员"));

        manager.removeSession(token);

        assertNull(manager.findValidSession(token));
    }
}
