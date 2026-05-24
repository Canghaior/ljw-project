package com.ljw.controller;

import com.ljw.dao.model.User;
import com.ljw.dispatch.IUserDispatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UserController {

    private final IUserDispatch userDispatch;

    public UserController(IUserDispatch userDispatch) {
        this.userDispatch = userDispatch;
    }

    @GetMapping("/user/list")
    public List<User> list() {
        return userDispatch.findAll();
    }

}
