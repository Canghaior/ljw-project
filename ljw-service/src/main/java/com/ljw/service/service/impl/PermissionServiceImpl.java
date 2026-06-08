package com.ljw.service.service.impl;

import com.ljw.service.mapper.PermissionMapper;
import com.ljw.service.service.IPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RBAC 权限业务实现。
 *
 * <p>当前每次权限检查都实时查询数据库，角色或权限关系修改后可以立即生效，
 * 不需要用户退出并重新登录。</p>
 */
@Service
public class PermissionServiceImpl implements IPermissionService {

    /** 查询用户角色和权限关系的 Mapper。 */
    private final PermissionMapper permissionMapper;

    /**
     * 创建权限业务服务。
     *
     * @param permissionMapper 权限查询 Mapper，由 Spring 自动注入
     */
    public PermissionServiceImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    /**
     * 判断用户是否拥有指定权限。
     */
    @Override
    public boolean hasPermission(Long userId, String permissionCode) {
        if (userId == null || permissionCode == null || permissionCode.isBlank()) {
            return false;
        }
        return findPermissionCodes(userId).contains(permissionCode);
    }

    /**
     * 查询用户拥有的有效角色编码。
     */
    @Override
    public List<String> findRoleCodes(Long userId) {
        if (userId == null) {
            return List.of();
        }
        return permissionMapper.findRoleCodesByUserId(userId);
    }

    /**
     * 查询用户拥有的有效权限编码。
     */
    @Override
    public List<String> findPermissionCodes(Long userId) {
        if (userId == null) {
            return List.of();
        }
        return permissionMapper.findPermissionCodesByUserId(userId);
    }
}
