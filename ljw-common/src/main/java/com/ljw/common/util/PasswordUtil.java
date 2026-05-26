package com.ljw.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码工具类。
 *
 * <p>数据库中不能保存明文密码，因此新增用户或重置密码时要先调用
 * {@link #encode(String)} 加密；登录时调用 {@link #matches(String, String)}
 * 校验明文密码和数据库密文是否匹配。</p>
 */
public class PasswordUtil {

    // BCrypt 每次加密同一个明文都会生成不同密文，但 matches 仍然可以正确校验。
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 加密明文密码。
     *
     * @param rawPassword 明文密码，例如 123456
     * @return BCrypt 加密后的密文
     */
    public static String encode(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    /**
     * 校验密码是否正确。
     *
     * @param rawPassword 前端传来的明文密码
     * @param encodedPassword 数据库存储的 BCrypt 密文
     * @return true 表示密码正确，false 表示密码错误
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }
}
