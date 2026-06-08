package com.ljw.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljw.common.exception.BizException;
import com.ljw.common.security.LoginSessionManager;
import com.ljw.common.security.LoginUser;
import com.ljw.common.util.PasswordUtil;
import com.ljw.dao.model.User;
import com.ljw.service.mapper.UserMapper;
import com.ljw.service.service.IUserService;
import com.ljw.vo.user.LoginRequest;
import com.ljw.vo.user.LoginResponse;
import com.ljw.vo.user.UserInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户业务实现类。
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * 手写登录会话管理器。
     *
     * <p>密码校验成功后由它创建 Token，退出登录时由它删除 Token。</p>
     */
    private final LoginSessionManager loginSessionManager;

    /**
     * 创建用户业务服务。
     *
     * @param loginSessionManager Spring 容器中的登录会话管理器
     */
    public UserServiceImpl(LoginSessionManager loginSessionManager) {
        this.loginSessionManager = loginSessionManager;
    }

    /**
     * 用户登录。
     *
     * <p>登录流程：校验参数 -> 查询用户 -> 校验状态 -> 校验密码 -> 生成 token -> 组装返回值。</p>
     *
     * @param request 登录请求参数
     * @return 登录成功后的 token 和用户信息
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        // 1. 基础参数校验，避免空用户名或空密码继续查库。
        if (request == null || isBlank(request.getUsername()) || isBlank(request.getPassword())) {
            throw new BizException("用户名或密码不能为空");
        }

        // 2. 根据用户名查询用户。username 应保证唯一，否则 one() 查询到多条会报错。
        User user = lambdaQuery()
                .eq(User::getUsername, request.getUsername())
                .one();

        // 3. 用户不存在时，统一提示用户名或密码错误，避免暴露用户名是否存在。
        if (user == null) {
            throw new BizException("用户名或密码错误");
        }

        // 4. 账号被禁用时，不允许登录。
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BizException("账号已被禁用");
        }

        // 5. 校验密码。前端传明文，数据库存 BCrypt 密文。
        if (!PasswordUtil.matches(request.getPassword(), user.getPassword())) {
            throw new BizException("用户名或密码错误");
        }

        // 6. 密码正确后创建服务端登录会话，并生成安全随机 Token 返回给客户端。
        LoginUser loginUser = new LoginUser(user.getId(), user.getUsername(), user.getNickname());
        String token = loginSessionManager.createSession(loginUser);

        // 7. 组装返回给前端的用户信息，不能包含 password。
        UserInfoVO userInfoVO = new UserInfoVO()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setNickname(user.getNickname());

        // 8. 返回登录结果。
        return new LoginResponse()
                .setToken(token)
                .setUser(userInfoVO);
    }

    /**
     * 退出登录时删除服务端保存的 Token，会话删除后该 Token 会立即失效。
     *
     * @param token 当前请求使用的 Token
     */
    @Override
    public void logout(String token) {
        loginSessionManager.removeSession(token);
    }

    /**
     * 根据用户 id 获取当前用户信息。
     *
     * <p>该方法主要给 /auth/me 使用。即使 token 合法，也要再次确认用户是否还存在、
     * 是否被禁用。</p>
     *
     * @param userId 用户 id
     * @return 用户基本信息
     */
    @Override
    public UserInfoVO getUserInfo(Long userId) {
        // 1. 登录上下文中的 userId 为空，说明请求上下文不正常。
        if (userId == null) {
            throw new BizException(401, "请先登录");
        }

        // 2. 根据用户 id 查询数据库。
        User user = getById(userId);

        // 3. 用户不存在，说明 token 中的用户已经无效。
        if (user == null) {
            throw new BizException(401, "用户不存在");
        }

        // 4. 用户被禁用后，不允许继续使用系统。
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BizException(401, "账号已被禁用");
        }

        // 5. 只返回前端需要展示的信息。
        return new UserInfoVO()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setNickname(user.getNickname());
    }

    /**
     * 查询用户展示列表。
     *
     * <p>数据库实体 User 中包含 password、status 等内部字段，
     * Controller 不应该直接返回实体，所以这里统一转换成 UserInfoVO。</p>
     *
     * @return 用户展示信息列表
     */
    @Override
    public List<UserInfoVO> findAllUserInfo() {
        return list().stream()
                // 实体转 VO，只暴露前端允许看到的字段。
                .map(user -> new UserInfoVO()
                        .setId(user.getId())
                        .setUsername(user.getUsername())
                        .setNickname(user.getNickname()))
                .toList();
    }

    /**
     * 判断字符串是否为空白。
     *
     * @param value 待判断字符串
     * @return true 表示 null、空字符串或全空格
     */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
