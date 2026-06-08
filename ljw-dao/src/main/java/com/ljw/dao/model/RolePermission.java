package com.ljw.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 角色权限关联实体，对应 sys_role_permission 表。
 *
 * <p>一条记录表示一个角色拥有一个权限。</p>
 */
@TableName("sys_role_permission")
public class RolePermission {

    /** 角色主键。 */
    private Long roleId;

    /** 权限主键。 */
    private Long permissionId;

    /** 关联关系创建时间。 */
    private LocalDateTime createTime;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
