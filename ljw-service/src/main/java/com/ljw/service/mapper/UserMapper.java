package com.ljw.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljw.dao.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 Mapper。
 *
 * <p>继承 BaseMapper 后，MyBatis-Plus 会提供常用的增删改查方法。</p>
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
