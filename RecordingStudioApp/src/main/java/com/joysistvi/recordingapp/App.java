package com.joysistvi.recordingapp;

import com.joysistvi.recordingapp.cliview.AlbumView;
import com.joysistvi.recordingapp.cliview.ArtistView;
import com.joysistvi.recordingapp.cliview.SongView;
import com.joysistvi.recordingapp.cliview.UserView;

import com.joysistvi.recordingapp.config.DbConnection;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.controller.ArtistController;
import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.controller.UserController;

import com.joysistvi.recordingapp.dao.AlbumDao;
import com.joysistvi.recordingapp.dao.UserDao;

import com.joysistvi.recordingapp.model.User;

import com.joysistvi.recordingapp.repository.ArtistRepo;
import com.joysistvi.recordingapp.repository.ArtistRepoImpl;
import com.joysistvi.recordingapp.repository.SongRepo;
import com.joysistvi.recordingapp.repository.SongRepoImpl;

import com.joysistvi.recordingapp.service.AlbumService;
import com.joysistvi.recordingapp.service.AlbumServiceImpl;
import com.joysistvi.recordingapp.service.ArtistService;
import com.joysistvi.recordingapp.service.ArtistServiceImpl;
import com.joysistvi.recordingapp.service.SongService;
import com.joysistvi.recordingapp.service.SongServiceImpl;
import com.joysistvi.recordingapp.service.UserService;
import com.joysistvi.recordingapp.service.UserServiceImpl;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DbConnection dbConnection =
                new DbConnection();

        // ==========================================
        // USER
        // ==========================================

        UserDao userDao =
                new UserDao(dbConnection);

        UserService userService =
                new UserServiceImpl(userDao);

        UserController userController =
                new UserController(userService);

        UserView userView =
                new UserView(
                        userController,
                        scanner
                );

        // ==========================================
        // LOGIN / REGISTER
        // ==========================================

        User loggedInUser =
                userView.showLoginMenu();

        // Exit if user chooses 0
        if (loggedInUser == null) {
            scanner.close();
            return;
        }

        // ==========================================
        // ARTIST
        // ==========================================

        ArtistRepo artistRepository =
                new ArtistRepoImpl(dbConnection);

        ArtistService artistService =
                new ArtistServiceImpl(artistRepository);

        ArtistController artistController =
                new ArtistController(artistService);

        // ==========================================
        // ALBUM
        // ==========================================

        AlbumDao albumDao =
                new AlbumDao(dbConnection);

        AlbumService albumService =
                new AlbumServiceImpl(albumDao);

        AlbumController albumController =
                new AlbumController(albumService);

        // ==========================================
        // SONG
        // ==========================================

        SongRepo songRepository =
                new SongRepoImpl(dbConnection);

        SongService songService =
                new SongServiceImpl(songRepository);

        SongController songController =
                new SongController(songService);

        // ==========================================
        // VIEWS
        // ==========================================

        ArtistView artistView =
                new ArtistView(
                        artistController,
                        albumController,
                        songController,
                        scanner
                );

        AlbumView albumView =
                new AlbumView(
                        albumController,
                        scanner
                );

        SongView songView =
                new SongView(
                        songController,
                        scanner
                );

        // ==========================================
        // MAIN MENU
        // ==========================================

        int choice;

        do {

            System.out.println();
            System.out.println("================================");
            System.out.println("      RECORDING STUDIO APP");
            System.out.println("================================");
            System.out.println(
                    "Logged in as: " +
                            loggedInUser.getUsername()
            );
            System.out.println("================================");
            System.out.println("1. Artist Management");
            System.out.println("2. Album Management");
            System.out.println("3. Song Management");
            System.out.println("0. Exit");
            System.out.println("================================");
            System.out.print("Choose: ");

            String input =
                    scanner.nextLine();

            try {

                choice =
                        Integer.parseInt(
                                input.trim()
                        );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );

                choice = -1;
                continue;
            }

            switch (choice) {

                case 1 ->
                        artistView.run();

                case 2 ->
                        albumView.run();

                case 3 ->
                        songView.run();

                case 0 -> {

                    System.out.println();
                    System.out.println(
                            "Thank you for using " +
                                    "Recording Studio App!"
                    );
                }

                default ->
                        System.out.println(
                                "Invalid choice. Try again."
                        );
            }

        } while (choice != 0);

        scanner.close();
    }
}