package com.ljw.vo.user;

public class LoginResponse {

    private String token;
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
