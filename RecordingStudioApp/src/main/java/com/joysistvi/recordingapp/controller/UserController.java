package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.service.UserService;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User handleLogin(String username, String password) {
        return userService.login(username, password);
    }

    public boolean handleRegister(User user) {
        return userService.register(user);
    }
}

