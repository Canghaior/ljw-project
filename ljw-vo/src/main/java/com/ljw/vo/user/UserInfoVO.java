package com.ljw.vo.user;

/**
 * 返回给前端的用户基本信息。
 *
 * <p>该对象不包含 password 字段，避免敏感信息泄露。</p>
 */
public class UserInfoVO {

    // 用户 id。
    private Long id;

    // 登录用户名。
    private String username;

    // 用户昵称。
    private String nickname;

    public Long getId() {
        return id;
    }

    public UserInfoVO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserInfoVO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public UserInfoVO setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }
}
