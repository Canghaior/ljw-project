package com.ljw.dispatch;

import com.ljw.vo.user.LoginRequest;
import com.ljw.vo.user.LoginResponse;
import com.ljw.vo.user.UserInfoVO;

import java.util.List;

/**
 * 用户业务编排接口。
 *
 * <p>Controller 不直接调用 service，而是通过 dispatch 层进入业务流程，
 * 后续如果要组合多个 service，可以放在这一层处理。</p>
 */
public interface IUserDispatch {

    /**
     * 查询用户展示列表。
     *
     * <p>这里返回 VO，不返回数据库实体，避免 Controller 泄露 password 等内部字段。</p>
     *
     * @return 用户展示信息列表
     */
    List<UserInfoVO> findAllUserInfo();

    /**
     * 用户登录。
     *
     * @param request 登录请求参数
     * @return 登录成功后的 token 和用户信息
     */
    LoginResponse login(LoginRequest request);

    /**
     * 退出当前登录会话。
     *
     * @param token 当前请求携带的 Token
     */
    void logout(String token);

    /**
     * 根据用户 id 获取用户基本信息。
     *
     * @param userId 用户 id
     * @return 用户基本信息
     */
    UserInfoVO getUserInfo(Long userId);
}
