package com.ljw.vo.user;

/**
 * 登录请求参数。
 */
public class LoginRequest {

    /**
     * 客户端提交的登录用户名。
     */
    private String username;

    /**
     * 客户端提交的明文密码。
     *
     * <p>它只在本次登录校验中使用，不应记录到日志或直接保存到数据库。</p>
     */
    private String password;

    /**
     * 获取客户端提交的用户名。
     *
     * @return 登录用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置客户端提交的用户名。
     *
     * <p>Jackson 把请求 JSON 转成 LoginRequest 时会调用该方法。</p>
     *
     * @param username 登录用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取客户端提交的明文密码。
     *
     * @return 明文密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置客户端提交的明文密码。
     *
     * @param password 明文密码
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
