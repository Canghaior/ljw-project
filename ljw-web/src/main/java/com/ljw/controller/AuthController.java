package com.ljw.controller;
import com.ljw.common.Result;
import com.ljw.dispatch.IUserDispatch;
import com.ljw.vo.user.LoginRequest;
import com.ljw.vo.user.LoginResponse;
import org.springframework.web.bind.annotation.*;

/**
 * 登录相关接口
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IUserDispatch userDispatch;

    public AuthController(IUserDispatch userDispatch) {
        this.userDispatch = userDispatch;
    }

    /**
     * 登录接口
     *
     * 请求地址：
     * POST /auth/login
     *
     * 请求体：
     * {
     *   "username": "admin",
     *   "password": "123456"
     * }
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        // controller 不直接查数据库，只负责接收请求和返回结果
        LoginResponse response = userDispatch.login(request);
        return Result.success(response);
    }
}
