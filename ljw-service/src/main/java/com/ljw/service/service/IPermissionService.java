package com.ljw.service.service;

import java.util.List;

/**
 * RBAC 权限业务接口。
 */
public interface IPermissionService {

    /**
     * 判断用户是否拥有指定权限。
     *
     * @param userId 用户主键
     * @param permissionCode 权限编码
     * @return true 表示拥有权限，false 表示没有权限
     */
    boolean hasPermission(Long userId, String permissionCode);

    /**
     * 查询用户拥有的有效角色编码。
     *
     * @param userId 用户主键
     * @return 角色编码集合
     */
    List<String> findRoleCodes(Long userId);

    /**
     * 查询用户拥有的有效权限编码。
     *
     * @param userId 用户主键
     * @return 权限编码集合
     */
    List<String> findPermissionCodes(Long userId);
}
