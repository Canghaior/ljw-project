package com.ljw.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljw.dao.model.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联表 Mapper。
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
