package com.ljw.common.security;

import java.io.Serializable;

/**
 * 当前登录用户信息。
 *
 * <p>它表示“已经通过认证的用户”，是业务层可以识别的用户身份对象。
 * 这里只保存系统运行需要的基础身份字段，不保存 password 等敏感信息。</p>
 */
public class LoginUser implements Serializable {

    // 当前登录用户 id，系统内部做数据归属、操作人记录时主要使用它。
    private final Long userId;

    // 当前登录用户名，适合用于日志、审计、页面展示等场景。
    private final String username;

    // 当前登录用户昵称，属于非敏感展示信息。
    private final String nickname;

    public LoginUser(Long userId, String username, String nickname) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }
}
