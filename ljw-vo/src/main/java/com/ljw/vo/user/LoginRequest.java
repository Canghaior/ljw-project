package com.ljw.vo.user;

/**
 * 登录请求参数。
 */
public class LoginRequest {

    // 登录用户名。
    private String username;

    // 前端传来的明文密码，只用于登录校验，不直接入库。
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
