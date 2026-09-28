package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.User;

public interface UserService {

    User login(String username, String password);

    boolean register(User user);
}

