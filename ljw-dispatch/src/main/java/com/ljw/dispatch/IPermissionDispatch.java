package com.ljw.dispatch;

/**
 * 权限检查编排接口。
 *
 * <p>Web 层通过 Dispatch 调用权限业务，避免权限拦截器直接依赖 Service 实现。</p>
 */
public interface IPermissionDispatch {

    /**
     * 判断用户是否拥有指定权限。
     *
     * @param userId 用户主键
     * @param permissionCode 权限编码
     * @return true 表示拥有权限
     */
    boolean hasPermission(Long userId, String permissionCode);
}
