package com.ljw.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

/**
 * JWT 工具类。
 *
 * <p>登录成功后创建 token；访问受保护接口时解析 token，取出用户 id。</p>
 */
@Component
public class JwtUtil {

    // JWT 签名密钥对象。密钥来自配置文件或环境变量，不再写死在代码逻辑中。
    private final SecretKey key;

    // token 有效期，单位是毫秒。
    private final long expireTimeMillis;

    /**
     * 创建 JWT 工具对象。
     *
     * <p>真实项目中 JWT_SECRET 应该通过环境变量传入，不能提交生产密钥。
     * HS256 密钥长度至少需要 32 字节，否则 jjwt 会拒绝创建签名对象。</p>
     *
     * @param secret JWT 签名密钥
     * @param expireHours token 有效小时数
     */
    public JwtUtil(@Value("${jwt.secret:ljw-project-login-secret-key-please-change-32}") String secret,
                   @Value("${jwt.expire-hours:24}") long expireHours) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expireTimeMillis = Duration.ofHours(expireHours).toMillis();
    }

    /**
     * 创建 token。
     *
     * @param userId 用户 id，放入 subject
     * @param username 用户名，放入 claim
     * @param nickname 用户昵称，放入 claim
     * @return JWT token 字符串
     */
    public String createToken(Long userId, String username, String nickname) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expireTimeMillis);

        return Jwts.builder()
                // subject 一般放用户唯一标识，这里放用户 id。
                .subject(String.valueOf(userId))
                // claim 只放非敏感身份信息，不要放 password、手机号等敏感字段。
                .claim("username", username)
                .claim("nickname", nickname)
                // token 签发时间。
                .issuedAt(now)
                // token 过期时间。
                .expiration(expireDate)
                // 使用密钥签名，防止 token 被篡改。
                .signWith(key)
                .compact();
    }

    /**
     * 解析 token。
     *
     * <p>如果 token 过期、格式错误、签名被篡改，这个方法会抛出异常。</p>
     *
     * @param token JWT token 字符串
     * @return token 中的载荷信息
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 从 token 中获取用户 id。
     *
     * @param token JWT token 字符串
     * @return 用户 id
     */
    public Long getUserId(String token) {
        return Long.valueOf(parseToken(token).getSubject());
    }

    /**
     * 从 token 中获取用户名。
     *
     * @param token JWT token 字符串
     * @return 用户名
     */
    public String getUsername(String token) {
        return parseToken(token).get("username", String.class);
    }
}
