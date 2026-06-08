package com.ljw.controller;

import com.ljw.common.Result;
import com.ljw.common.permission.RequirePermission;
import com.ljw.dispatch.IUserDispatch;
import com.ljw.vo.user.UserInfoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户相关接口。
 */
@RestController
public class UserController {

    private final IUserDispatch userDispatch;

    public UserController(IUserDispatch userDispatch) {
        this.userDispatch = userDispatch;
    }

    /**
     * 查询用户列表。
     *
     * <p>该接口由自定义登录拦截器保护，访问时需要在请求头中携带 Bearer Token。
     * 返回值使用 VO，不直接返回数据库实体，避免泄露 password 等内部字段。</p>
     *
     * @return 用户列表
     */
    @GetMapping("/user/list")
    @RequirePermission("user:list")
    public Result<List<UserInfoVO>> list() {
        List<UserInfoVO> users = userDispatch.findAllUserInfo();
        return Result.success(users);
    }
}
