package com.ljw.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljw.common.util.JwtUtil;
import com.ljw.common.util.PasswordUtil;
import com.ljw.dao.model.User;
import com.ljw.service.mapper.UserMapper;
import com.ljw.service.service.IUserService;
import com.ljw.vo.user.LoginRequest;
import com.ljw.vo.user.LoginResponse;
import com.ljw.vo.user.UserInfoVO;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * 登录业务
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        //1. 根据用户名查询用户
        User user = lambdaQuery().eq(User::getUsername, request.getUsername()).one();

        // 2. 用户不存在时，不要明确告诉前端“用户名不存在”
        // 统一提示“用户名或密码错误”，这样更安全
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 3. 判断账号状态
        // status = 1 表示正常，其他情况都不允许登录
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new RuntimeException("账号已被禁用");
        }

        // 4. 校验密码
        // request.getPassword() 是前端传来的明文密码
        // user.getPassword() 是数据库里的 BCrypt 密文
        if (!PasswordUtil.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 5. 密码正确，生成 token
        String token = JwtUtil.createToken(user.getId(), user.getUsername());

        // 6. 组装返回给前端的用户信息
        // 注意：不要把 password 返回给前端
        UserInfoVO userInfoVO = new UserInfoVO().setId(user.getId()).setUsername(user.getUsername()).setNickname(user.getNickname());

        // 7. 返回登录结果
        return new LoginResponse().setToken(token).setUser(userInfoVO);
    }
}
