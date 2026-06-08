package com.ljw.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljw.dao.model.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色表 Mapper，提供角色的基础数据库操作。
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
