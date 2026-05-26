package com.ljw.dispatch.impl;

import com.ljw.dao.model.User;
import com.ljw.dispatch.IUserDispatch;
import com.ljw.service.service.IUserService;
import com.ljw.vo.user.LoginRequest;
import com.ljw.vo.user.LoginResponse;
import com.ljw.vo.user.UserInfoVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户业务编排实现。
 */
@Component
public class UserDispatchImpl implements IUserDispatch {

    private final IUserService userService;

    public UserDispatchImpl(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 查询全部用户。
     *
     * @return 用户列表
     */
    @Override
    public List<User> findAll() {
        return userService.list();
    }

    /**
     * 用户登录。
     *
     * @param request 登录请求参数
     * @return 登录成功后的 token 和用户信息
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        return userService.login(request);
    }

    /**
     * 根据用户 id 获取用户基本信息。
     *
     * @param userId 用户 id
     * @return 用户基本信息
     */
    @Override
    public UserInfoVO getUserInfo(Long userId) {
        return userService.getUserInfo(userId);
    }
}
