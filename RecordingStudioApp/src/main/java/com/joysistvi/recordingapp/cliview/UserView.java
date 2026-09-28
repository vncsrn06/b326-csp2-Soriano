package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.UserController;
import com.joysistvi.recordingapp.model.User;

import java.util.Scanner;

public class UserView {

    private final UserController userController;
    private final Scanner scanner;

    public UserView(UserController userController, Scanner scanner) {
        this.userController = userController;
        this.scanner = scanner;
    }

    public User showLoginMenu() {

        while (true) {

            System.out.println();
            System.out.println("==============================");
            System.out.println("      RECORDING STUDIO APP");
            System.out.println("==============================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.println("==============================");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice.trim()) {

                case "1" -> {

                    User user = login();

                    if (user != null) {
                        return user;
                    }
                }

                case "2" -> register();

                case "0" -> {

                    System.out.println();
                    System.out.println(
                            "Thank you for using Recording Studio App!"
                    );

                    return null;
                }

                default ->
                        System.out.println(
                                "Invalid choice. Try again."
                        );
            }
        }
    }

    private User login() {

        System.out.println();
        System.out.println("==============================");
        System.out.println("            LOGIN");
        System.out.println("==============================");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user =
                userController.handleLogin(
                        username,
                        password
                );

        if (user != null) {

            System.out.println();
            System.out.println("Login successful!");
            System.out.println(
                    "Welcome, " + user.getUsername() + "!"
            );

            return user;

        } else {

            System.out.println();
            System.out.println(
                    "Invalid username or password."
            );

            return null;
        }
    }

    private void register() {

        System.out.println();
        System.out.println("==============================");
        System.out.println("          REGISTER");
        System.out.println("==============================");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user =
                new User(
                        username,
                        password
                );

        boolean success =
                userController.handleRegister(user);

        if (success) {

            System.out.println();
            System.out.println(
                    "Registration successful!"
            );

        } else {

            System.out.println();
            System.out.println(
                    "Registration failed."
            );
        }
    }
}