package com.ljw.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljw.dao.model.User;
import com.ljw.vo.user.LoginRequest;
import com.ljw.vo.user.LoginResponse;
import com.ljw.vo.user.UserInfoVO;

/**
 * 用户业务接口。
 */
public interface IUserService extends IService<User> {

    /**
     * 用户登录。
     *
     * @param loginRequest 登录请求参数
     * @return 登录成功后的 token 和用户信息
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * 根据用户 id 获取前端可展示的用户信息。
     *
     * @param userId 用户 id
     * @return 用户基本信息
     */
    UserInfoVO getUserInfo(Long userId);
}
