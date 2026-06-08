package com.ljw.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljw.dao.model.RolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色权限关联表 Mapper。
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}
