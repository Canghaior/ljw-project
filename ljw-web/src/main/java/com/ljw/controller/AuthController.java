package com.ljw.controller;

import com.ljw.common.Result;
import com.ljw.common.security.LoginUserContext;
import com.ljw.dispatch.IUserDispatch;
import com.ljw.vo.user.LoginRequest;
import com.ljw.vo.user.LoginResponse;
import com.ljw.vo.user.UserInfoVO;
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

    /**
     * 用户业务编排入口。
     *
     * <p>Controller 只处理 HTTP 输入输出，登录和退出逻辑继续交给下层完成。</p>
     */
    private final IUserDispatch userDispatch;

    /**
     * 创建认证接口控制器。
     *
     * @param userDispatch 用户业务编排对象，由 Spring 自动注入
     */
    public AuthController(IUserDispatch userDispatch) {
        this.userDispatch = userDispatch;
    }

    /**
     * 登录接口。
     *
     * <p>前端提交用户名和密码，后端校验通过后创建服务端会话，
     * 返回安全随机 Token 和用户基本信息。</p>
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
     * <p>登录拦截器会先校验 Token，并把登录会话放入自建 ThreadLocal 上下文。
     * 这里不依赖 HttpServletRequest，避免 Web 层对象向业务层扩散。</p>
     *
     * @return 当前登录用户信息
     */
    @GetMapping("/me")
    public Result<UserInfoVO> me() {
        // 从自建登录上下文获取当前用户 id。
        Long userId = LoginUserContext.getUserId();
        UserInfoVO userInfo = userDispatch.getUserInfo(userId);
        return Result.success(userInfo);
    }

    /**
     * 退出登录。
     *
     * <p>服务端删除当前 Token 对应的内存会话，客户端随后也应删除本地 Token。</p>
     *
     * @return 退出成功
     */
    @PostMapping("/logout")
    public Result<Void> logout() {
        // 拦截器已经完成 Token 校验，因此这里可以直接从上下文获取当前 Token。
        userDispatch.logout(LoginUserContext.getToken());
        return Result.success(null);
    }
}
