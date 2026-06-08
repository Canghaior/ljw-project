package com.ljw.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 权限数据库实体，对应 sys_permission 表。
 */
@TableName("sys_permission")
public class Permission {

    /** 权限主键。 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 权限唯一编码，例如 user:list。 */
    private String permissionCode;

    /** 权限展示名称，例如查看用户列表。 */
    private String permissionName;

    /** 权限状态：1 正常，0 禁用。 */
    private Integer status;

    /** 权限创建时间。 */
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
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
