package com.joysistvi.recordingapp;

import com.joysistvi.recordingapp.cliview.UserView;
import com.joysistvi.recordingapp.cliview.UserView;
import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.controller.UserController;
import com.joysistvi.recordingapp.dao.UserDao;
import com.joysistvi.recordingapp.service.UserService;
import com.joysistvi.recordingapp.service.UserServiceImpl;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Database connection
        DbConnection dbConnection = new DbConnection();

        // DAO
        UserDao userDao = new UserDao(dbConnection);

        // Service
        UserService userService = new UserServiceImpl(userDao);

        // Controller
        UserController userController = new UserController(userService);

        Scanner scanner = new Scanner(System.in);

        UserView userView = new UserView(userController, scanner);

        userView.showLoginMenu();

        scanner.close();
    }
}