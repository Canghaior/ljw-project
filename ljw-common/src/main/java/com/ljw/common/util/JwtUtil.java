package com.ljw.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 工具类。
 *
 * <p>登录成功后创建 token；访问受保护接口时解析 token，取出用户 id。</p>
 */
public class JwtUtil {

    /**
     * JWT 签名密钥。
     *
     * <p>学习阶段先写在代码中。真实项目中建议放到 application.yml 或环境变量里，
     * 并且不要提交真实生产密钥。</p>
     */
    private static final String SECRET = "ljw-project-login-secret-key-please-change-32";

    // token 有效期：24 小时。
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24;

    // 根据密钥字符串生成 JWT 签名对象。
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * 创建 token。
     *
     * @param userId 用户 id，放入 subject
     * @param username 用户名，放入 claim
     * @return JWT token 字符串
     */
    public static String createToken(Long userId, String username) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRE_TIME);

        return Jwts.builder()
                // subject 一般放用户唯一标识，这里放用户 id。
                .subject(String.valueOf(userId))
                // claim 用来放额外信息，这里放用户名，方便后续读取。
                .claim("username", username)
                // token 签发时间。
                .issuedAt(now)
                // token 过期时间。
                .expiration(expireDate)
                // 使用密钥签名，防止 token 被篡改。
                .signWith(KEY)
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
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
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
    public static Long getUserId(String token) {
        return Long.valueOf(parseToken(token).getSubject());
    }

    /**
     * 从 token 中获取用户名。
     *
     * @param token JWT token 字符串
     * @return 用户名
     */
    public static String getUsername(String token) {
        return parseToken(token).get("username", String.class);
    }
}
