package com.ljw.common.security;

import java.time.LocalDateTime;

/**
 * 服务端保存的一次登录会话。
 *
 * <p>随机 Token 只返回给客户端并作为 Map 的 key 使用；会话对象保存当前用户和过期时间。
 * 这种方式不依赖 JWT，服务端可以主动删除会话来实现退出登录。</p>
 */
public class LoginSession {

    /**
     * 当前会话对应的随机 Token。
     *
     * <p>客户端后续请求会携带这个值，服务端用它作为 key 查询会话。</p>
     */
    private final String token;

    /**
     * 已经通过用户名、密码和账号状态校验的用户。
     */
    private final LoginUser loginUser;

    /**
     * 会话的绝对过期时间。
     *
     * <p>当前时间达到或超过该时间后，Token 不能继续访问受保护接口。</p>
     */
    private final LocalDateTime expireTime;

    /**
     * 创建一次完整的服务端登录会话。
     *
     * @param token 服务端生成的安全随机 Token
     * @param loginUser Token 对应的登录用户
     * @param expireTime 会话过期时间
     */
    public LoginSession(String token, LoginUser loginUser, LocalDateTime expireTime) {
        this.token = token;
        this.loginUser = loginUser;
        this.expireTime = expireTime;
    }

    /**
     * 获取当前会话的 Token。
     *
     * @return 随机 Token
     */
    public String getToken() {
        return token;
    }

    /**
     * 获取当前会话对应的登录用户。
     *
     * @return 登录用户
     */
    public LoginUser getLoginUser() {
        return loginUser;
    }

    /**
     * 获取当前会话的过期时间。
     *
     * @return 会话过期时间
     */
    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    /**
     * 判断会话在指定时间是否已经过期。
     *
     * @param now 当前时间
     * @return true 表示已经过期
     */
    public boolean isExpired(LocalDateTime now) {
        return !expireTime.isAfter(now);
    }
}
