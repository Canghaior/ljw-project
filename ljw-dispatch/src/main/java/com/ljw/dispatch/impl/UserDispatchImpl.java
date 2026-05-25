package com.ljw.dispatch.impl;
import com.ljw.dao.model.User;
import com.ljw.dispatch.IUserDispatch;
import com.ljw.service.service.IUserService;
import com.ljw.vo.user.LoginRequest;
import com.ljw.vo.user.LoginResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDispatchImpl implements IUserDispatch {

    private final IUserService userService;

    public UserDispatchImpl(IUserService userService) {

        this.userService = userService;
    }

    /**
     * 查询全部用户
     */

    @Override
    public List<User> findAll() {
        return userService.list();
    }

    /**
     * 登录
     * dispatch 层主要做业务编排，这里先直接调用 service
     */
    @Override
    public  LoginResponse login(LoginRequest request) {
        return userService.login(request);
    }
}
