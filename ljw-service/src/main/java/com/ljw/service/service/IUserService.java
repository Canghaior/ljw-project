package com.ljw.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljw.dao.model.User;
import com.ljw.vo.user.LoginRequest;
import com.ljw.vo.user.LoginResponse;

public interface IUserService extends IService<User> {

    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest loginRequest);
}
