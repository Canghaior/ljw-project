package com.ljw.dispatch.impl;

import com.ljw.dispatch.IPermissionDispatch;
import com.ljw.service.service.IPermissionService;
import org.springframework.stereotype.Component;

/**
 * 权限检查编排实现。
 */
@Component
public class PermissionDispatchImpl implements IPermissionDispatch {

    /** RBAC 权限业务服务。 */
    private final IPermissionService permissionService;

    /**
     * 创建权限编排对象。
     *
     * @param permissionService 权限业务服务，由 Spring 自动注入
     */
    public PermissionDispatchImpl(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * 把权限判断交给权限业务层执行。
     */
    @Override
    public boolean hasPermission(Long userId, String permissionCode) {
        return permissionService.hasPermission(userId, permissionCode);
    }
}
