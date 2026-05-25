package com.ljw.dispatch;

import com.ljw.dao.model.User;
import com.ljw.vo.user.LoginResponse;
import com.ljw.vo.user.LoginRequest;
import java.util.List;

public interface IUserDispatch {


    /**
     * 查询全部用户
     */
    List<User> findAll();

    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest request);
}
