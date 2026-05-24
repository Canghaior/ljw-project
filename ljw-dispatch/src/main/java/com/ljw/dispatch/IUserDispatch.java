package com.ljw.dispatch;

import com.ljw.dao.model.User;
import java.util.List;

public interface IUserDispatch {
    List<User> findAll();
}
