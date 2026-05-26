package com.ljw.controller;

import com.ljw.common.Result;
import com.ljw.dispatch.IUserDispatch;
import com.ljw.vo.user.LoginRequest;
import com.ljw.vo.user.LoginResponse;
import com.ljw.vo.user.UserInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录认证相关接口。
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IUserDispatch userDispatch;

    public AuthController(IUserDispatch userDispatch) {
        this.userDispatch = userDispatch;
    }

    /**
     * 登录接口。
     *
     * <p>前端提交用户名和密码，后端校验通过后返回 JWT token 和用户基本信息。</p>
     *
     * @param request 登录请求参数
     * @return token 和用户信息
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        // Controller 只负责接收请求和返回结果，具体登录逻辑交给 dispatch/service。
        LoginResponse response = userDispatch.login(request);
        return Result.success(response);
    }

    /**
     * 获取当前登录用户信息。
     *
     * <p>LoginInterceptor 会先解析 token，并把 userId 放入 request。
     * 这里直接通过 userId 查询当前用户信息。</p>
     *
     * @param request HTTP 请求对象
     * @return 当前登录用户信息
     */
    @GetMapping("/me")
    public Result<UserInfoVO> me(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        UserInfoVO userInfo = userDispatch.getUserInfo(userId);
        return Result.success(userInfo);
    }

    /**
     * 退出登录。
     *
     * <p>当前是简单 JWT 方案，后端不保存 token 状态；退出时前端删除本地 token 即可。</p>
     *
     * @return 退出成功
     */
    @PostMapping("/logout")
    public Result<Void> logout() {
        return Result.success(null);
    }
}
