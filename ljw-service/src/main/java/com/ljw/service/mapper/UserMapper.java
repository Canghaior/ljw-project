package com.ljw.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljw.dao.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends  BaseMapper<User> {
}
