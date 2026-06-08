package com.ljw.vo.user;

/**
 * 登录成功返回数据。
 */
public class LoginResponse {

    /**
     * 登录成功后由服务端生成并保存的安全随机 Token。
     *
     * <p>客户端后续请求需要把它放入 Authorization 请求头。</p>
     */
    private String token;

    /**
     * 当前登录用户允许返回给客户端的基本信息，不包含密码。
     */
    private UserInfoVO user;

    /**
     * 获取登录 Token。
     *
     * @return 服务端生成的随机 Token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置登录 Token。
     *
     * @param token 服务端生成的随机 Token
     * @return 当前对象，便于链式调用
     */
    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * 获取当前登录用户的展示信息。
     *
     * @return 不包含密码的用户信息
     */
    public UserInfoVO getUser() {
        return user;
    }

    /**
     * 设置当前登录用户的展示信息。
     *
     * @param user 不包含密码的用户信息
     * @return 当前对象，便于链式调用
     */
    public LoginResponse setUser(UserInfoVO user) {
        this.user = user;
        return this;
    }
}
