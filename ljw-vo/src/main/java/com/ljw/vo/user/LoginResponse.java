package com.ljw.vo.user;

/**
 * 登录成功返回数据。
 */
public class LoginResponse {

    // 登录成功后生成的 JWT token。
    private String token;

    // 当前登录用户的基本信息，不包含密码。
    private UserInfoVO user;

    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public UserInfoVO getUser() {
        return user;
    }

    public LoginResponse setUser(UserInfoVO user) {
        this.user = user;
        return this;
    }
}
