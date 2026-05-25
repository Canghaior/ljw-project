package com.ljw.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码工具类，负责密码加密和密码校验。
 */
public class PasswordUtil {

    // BCrypt 密码加密器。
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 加密明文密码。
     *
     * @param rawPassword 明文密码，例如 123456
     * @return BCrypt 加密后的密码
     */
    public static String encode(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    /**
     * 校验密码是否正确。
     *
     * @param rawPassword 前端传来的明文密码
     * @param encodedPassword 数据库存储的加密密码
     * @return true 表示密码正确，false 表示密码错误
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }
}
