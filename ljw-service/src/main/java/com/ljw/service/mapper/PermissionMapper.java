package com.ljw.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljw.dao.model.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 权限 Mapper。
 *
 * <p>除了权限表的基础操作，还负责沿着“用户 -> 角色 -> 权限”关系查询用户权限。</p>
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 查询用户当前拥有的全部有效权限编码。
     *
     * <p>DISTINCT 用于避免用户通过多个角色获得同一权限时返回重复编码。</p>
     *
     * @param userId 用户主键
     * @return 有效权限编码集合
     */
    @Select("""
            SELECT DISTINCT p.permission_code
            FROM sys_user_role ur
            INNER JOIN sys_role r
                    ON r.id = ur.role_id
                   AND r.status = 1
            INNER JOIN sys_role_permission rp
                    ON rp.role_id = r.id
            INNER JOIN sys_permission p
                    ON p.id = rp.permission_id
                   AND p.status = 1
            WHERE ur.user_id = #{userId}
            ORDER BY p.permission_code
            """)
    List<String> findPermissionCodesByUserId(@Param("userId") Long userId);

    /**
     * 查询用户当前拥有的全部有效角色编码。
     *
     * @param userId 用户主键
     * @return 有效角色编码集合
     */
    @Select("""
            SELECT DISTINCT r.role_code
            FROM sys_user_role ur
            INNER JOIN sys_role r
                    ON r.id = ur.role_id
                   AND r.status = 1
            WHERE ur.user_id = #{userId}
            ORDER BY r.role_code
            """)
    List<String> findRoleCodesByUserId(@Param("userId") Long userId);
}
