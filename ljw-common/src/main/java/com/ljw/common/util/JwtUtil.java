package com.ljw.common.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


/**
 * JWT 工具类
 * 登录成功后，用它生成 token
 */
public class JwtUtil {


    /**
     * JWT 密钥
     * 注意：真实项目里不要写死在代码里，应该放到 application.yml
     * 这里先为了学习方便写死
     *
     * 这个字符串长度必须足够长，否则 JWT 会报错
     */
    private static final String SECRET = "ljw-project-login-secret-key-please-change-32";

    /**
     * token 过期时间
     * 这里设置为 24 小时
     */
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24;

    //根据 SECRET 生成签名密钥
    private static final  SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));


    /**
     * 创建 token
     *
     * @param userId 用户 id
     * @param username 用户名
     */
    public static String createToken(Long userId, String username) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRE_TIME);

        return Jwts.builder()
                // subject 一般放用户唯一标识，这里放用户 id
                .subject(String.valueOf(userId))
                // claim 可以放一些额外信息
                .claim("username", username)
                // token 签发时间
                .issuedAt(now)
                // token 过期时间
                .expiration(expireDate)
                // 使用密钥签名
                .signWith(KEY)
                .compact();
    }
}
