package com.ljw.controller;

import com.ljw.dao.model.User;
import com.ljw.dispatch.IUserDispatch;
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
     * <p>该接口会被登录拦截器保护，访问时需要在请求头中携带 token。</p>
     *
     * @return 用户列表
     */
    @GetMapping("/user/list")
    public List<User> list() {
        return userDispatch.findAll();
    }
}
