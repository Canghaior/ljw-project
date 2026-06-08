package com.ljw.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 角色数据库实体，对应 sys_role 表。
 */
@TableName("sys_role")
public class Role {

    /** 角色主键。 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 角色唯一编码，例如 ADMIN。 */
    private String roleCode;

    /** 角色展示名称，例如管理员。 */
    private String roleName;

    /** 角色状态：1 正常，0 禁用。 */
    private Integer status;

    /** 角色创建时间。 */
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
