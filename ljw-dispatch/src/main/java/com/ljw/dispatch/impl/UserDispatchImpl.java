package com.ljw.dispatch.impl;
import com.ljw.dao.model.User;
import com.ljw.dispatch.IUserDispatch;
import com.ljw.service.service.IUserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDispatchImpl implements IUserDispatch {

    private final IUserService userService;

    public UserDispatchImpl(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public List<User> findAll() {
        return userService.list();
    }
}
