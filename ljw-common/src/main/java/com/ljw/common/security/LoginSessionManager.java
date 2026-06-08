package com.ljw.common.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 手写登录会话管理器。
 *
 * <p>使用 JDK 的 SecureRandom 生成不可预测的 Token，并用线程安全 Map 保存登录状态。
 * 当前实现适合单机学习项目；项目重启后会话会清空，多实例部署时应替换为 Redis 等共享存储。</p>
 */
@Component
public class LoginSessionManager {

    /**
     * 生成 Token 时使用的随机字节数。
     *
     * <p>32 字节等于 256 位随机数据，Base64 URL 编码后可以安全放入 HTTP 请求头。</p>
     */
    private static final int TOKEN_BYTE_LENGTH = 32;

    /**
     * 服务端登录会话存储。
     *
     * <p>key 是客户端持有的 Token，value 是用户和过期时间组成的会话。
     * ConcurrentHashMap 支持多个请求线程并发创建、读取和删除会话。</p>
     */
    private final Map<String, LoginSession> sessionStore = new ConcurrentHashMap<>();

    /**
     * JDK 提供的密码学安全随机数生成器。
     *
     * <p>Token 必须难以预测，因此不能使用普通 Random、用户 id 或时间戳代替。</p>
     */
    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * 每个登录会话允许存活的时长。
     */
    private final Duration tokenExpireDuration;

    /**
     * 创建登录会话管理器。
     *
     * <p>Spring 会从配置项 {@code login.token-expire-hours} 注入有效小时数；
     * 未配置时使用 24 小时。</p>
     *
     * @param tokenExpireHours Token 有效小时数，必须大于 0
     */
    public LoginSessionManager(@Value("${login.token-expire-hours:24}") long tokenExpireHours) {
        if (tokenExpireHours <= 0) {
            throw new IllegalArgumentException("登录 Token 有效期必须大于 0 小时");
        }
        this.tokenExpireDuration = Duration.ofHours(tokenExpireHours);
    }

    /**
     * 用户登录成功后创建并保存会话。
     *
     * @param loginUser 已通过密码校验的用户
     * @return 返回给客户端的随机 Token
     */
    public String createSession(LoginUser loginUser) {
        String token = generateToken();
        LocalDateTime expireTime = LocalDateTime.now().plus(tokenExpireDuration);
        sessionStore.put(token, new LoginSession(token, loginUser, expireTime));
        return token;
    }

    /**
     * 根据 Token 获取有效会话。
     *
     * <p>读取时同时检查过期时间；发现过期会话后立即从内存删除，避免无效数据长期积累。</p>
     *
     * @param token 客户端提交的 Token
     * @return 有效会话；Token 不存在或已过期时返回 null
     */
    public LoginSession findValidSession(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }

        LoginSession session = sessionStore.get(token);
        if (session == null) {
            return null;
        }

        if (session.isExpired(LocalDateTime.now())) {
            sessionStore.remove(token, session);
            return null;
        }

        return session;
    }

    /**
     * 删除指定 Token 对应的会话，实现服务端退出登录。
     *
     * @param token 当前请求使用的 Token
     */
    public void removeSession(String token) {
        if (token != null) {
            sessionStore.remove(token);
        }
    }

    /**
     * 使用 256 位安全随机数生成 URL 安全的 Token。
     *
     * @return 不包含空格、加号和斜杠的随机 Token
     */
    private String generateToken() {
        byte[] tokenBytes = new byte[TOKEN_BYTE_LENGTH];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}
