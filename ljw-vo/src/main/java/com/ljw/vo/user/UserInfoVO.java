package com.ljw.vo.user;

public class UserInfoVO {
    private Long id;
    private String username;
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
