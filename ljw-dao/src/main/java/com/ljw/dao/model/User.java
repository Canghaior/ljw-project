package com.ljw.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 用户数据库实体。
 *
 * <p>该类对应 sys_user 表。注意：实体中包含 password 字段，
 * 所以不要直接把 User 返回给前端。</p>
 */
@TableName("sys_user")
public class User {

    // 用户主键，自增 id。
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 登录用户名，建议在数据库中加唯一索引。
    private String username;

    // 用户昵称，用于前端展示。
    private String nickname;

    // 年龄，当前项目中的基础用户字段。
    private Integer age;

    // 创建时间。
    private LocalDateTime createTime;

    // BCrypt 加密后的密码，不能保存明文密码。
    private String password;

    // 用户状态：1 正常，0 禁用。
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
