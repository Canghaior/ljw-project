package com.ljw.common.security;

import java.io.Serializable;

/**
 * 当前登录用户信息。
 *
 * <p>它表示“已经通过认证的用户”，是业务层可以识别的用户身份对象。
 * 这里只保存系统运行需要的基础身份字段，不保存 password 等敏感信息。</p>
 */
public class LoginUser implements Serializable {

    /**
     * 当前登录用户的数据库主键。
     *
     * <p>业务代码可以用它查询用户、记录操作人或判断数据归属。</p>
     */
    private final Long userId;

    /**
     * 当前登录用户的登录名。
     *
     * <p>适合用于日志和审计，不用于再次校验密码。</p>
     */
    private final String username;

    /**
     * 当前登录用户的昵称，属于可以展示的非敏感信息。
     */
    private final String nickname;

    /**
     * 创建一个已经通过身份认证的用户对象。
     *
     * @param userId 用户数据库主键
     * @param username 登录用户名
     * @param nickname 用户昵称
     */
    public LoginUser(Long userId, String username, String nickname) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
    }

    /**
     * 获取当前用户的数据库主键。
     *
     * @return 用户 id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 获取当前用户的登录名。
     *
     * @return 登录用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 获取当前用户的昵称。
     *
     * @return 用户昵称
     */
    public String getNickname() {
        return nickname;
    }
}
