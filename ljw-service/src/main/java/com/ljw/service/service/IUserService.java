package com.ljw.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljw.dao.model.User;
import com.ljw.vo.user.LoginRequest;
import com.ljw.vo.user.LoginResponse;
import com.ljw.vo.user.UserInfoVO;

import java.util.List;

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
     * 删除当前登录会话。
     *
     * @param token 当前请求携带的 Token
     */
    void logout(String token);

    /**
     * 根据用户 id 获取前端可展示的用户信息。
     *
     * @param userId 用户 id
     * @return 用户基本信息
     */
    UserInfoVO getUserInfo(Long userId);

    /**
     * 查询用户展示列表。
     *
     * <p>真实项目中接口层不直接返回 User 实体，避免 password 等数据库字段泄露。</p>
     *
     * @return 用户展示信息列表
     */
    List<UserInfoVO> findAllUserInfo();
}
