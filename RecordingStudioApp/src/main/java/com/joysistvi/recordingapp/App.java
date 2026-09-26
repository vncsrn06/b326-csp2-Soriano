package com.joysistvi.recordingapp;

import com.joysistvi.recordingapp.cliview.ArtistView;
import com.joysistvi.recordingapp.config.DbConnection;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.controller.ArtistController;
import com.joysistvi.recordingapp.controller.SongController;

import com.joysistvi.recordingapp.repository.AlbumRepo;
import com.joysistvi.recordingapp.repository.AlbumRepoImpl;
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

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DbConnection dbConnection =
                new DbConnection();


        // ==========================================
        // ARTIST
        // ==========================================

        ArtistRepo artistRepository =
                new ArtistRepoImpl(dbConnection);

        ArtistService artistService =
                new ArtistServiceImpl(
                        artistRepository
                );

        ArtistController artistController =
                new ArtistController(
                        artistService
                );


        // ==========================================
        // ALBUM
        // ==========================================

        AlbumRepo albumRepository =
                new AlbumRepoImpl(dbConnection);

        AlbumService albumService =
                new AlbumServiceImpl(
                        albumRepository
                );

        AlbumController albumController =
                new AlbumController(
                        albumService
                );


        // ==========================================
        // SONG
        // ==========================================

        SongRepo songRepository =
                new SongRepoImpl(dbConnection);

        SongService songService =
                new SongServiceImpl(
                        songRepository
                );

        SongController songController =
                new SongController(
                        songService
                );


        // ==========================================
        // ARTIST VIEW
        // ==========================================

        ArtistView artistView =
                new ArtistView(
                        artistController,
                        albumController,
                        songController,
                        scanner
                );
        // ==========================================
        // START APPLICATION
        // ==========================================

        artistView.run();


        scanner.close();
    }
}
