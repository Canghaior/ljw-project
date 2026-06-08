package com.ljw.dispatch.impl;

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

    /**
     * 用户领域业务服务。
     *
     * <p>Dispatch 当前只做调用转发，未来组合多个 Service 时可以在这一层编排。</p>
     */
    private final IUserService userService;

    /**
     * 创建用户业务编排对象。
     *
     * @param userService 用户业务服务，由 Spring 自动注入
     */
    public UserDispatchImpl(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 查询用户展示列表。
     *
     * @return 用户展示信息列表
     */
    @Override
    public List<UserInfoVO> findAllUserInfo() {
        return userService.findAllUserInfo();
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
     * 删除当前 Token 对应的服务端会话。
     *
     * @param token 当前请求携带的 Token
     */
    @Override
    public void logout(String token) {
        userService.logout(token);
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
