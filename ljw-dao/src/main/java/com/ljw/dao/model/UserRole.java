package com.ljw.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 用户角色关联实体，对应 sys_user_role 表。
 *
 * <p>一条记录表示一个用户拥有一个角色。</p>
 */
@TableName("sys_user_role")
public class UserRole {

    /** 用户主键。 */
    private Long userId;

    /** 角色主键。 */
    private Long roleId;

    /** 关联关系创建时间。 */
    private LocalDateTime createTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
