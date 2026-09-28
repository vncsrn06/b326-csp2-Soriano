package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.dao.UserDao;
import com.joysistvi.recordingapp.model.User;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(String username, String password) {
        return userDao.login(username, password);
    }

    @Override
    public boolean register(User user) {
        return userDao.register(user);
    }
}

