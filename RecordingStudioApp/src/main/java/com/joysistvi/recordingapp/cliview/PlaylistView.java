package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.PlaylistController;
import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.model.Song;

import java.util.List;
import java.util.Scanner;

public class PlaylistView {

    private final PlaylistController playlistController; // Composition
    private final Scanner scanner;
    private final int userId; // the logged-in user's own ID

    // Constructor injection
    public PlaylistView(PlaylistController playlistController, Scanner scanner, int userId) {
        this.playlistController = playlistController;
        this.scanner = scanner;
        this.userId = userId;
    }

    public void run() {
        int choice;

        do {
            printMenu();
            choice = promptChoice();

            switch (choice) {
                case 1 -> viewMyPlaylists();
                case 2 -> createPlaylist();
                case 3 -> deletePlaylist();
                case 4 -> viewSongsInPlaylist();
                case 5 -> addSongToPlaylist();
                case 6 -> removeSongFromPlaylist();
                case 0 -> System.out.println("Returning to dashboard...");
                default -> System.out.println("Invalid choice. Try again.");
            }

            if (choice != 0) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }

        } while (choice != 0);
    }

    private void printMenu() {
        printHeader("MY PLAYLISTS");

        System.out.println("1. View My Playlists");
        System.out.println("2. Create Playlist");
        System.out.println("3. Delete Playlist");
        System.out.println("4. View Songs in Playlist");
        System.out.println("5. Add Song to Playlist");
        System.out.println("6. Remove Song from Playlist");
        System.out.println("0. Back");
    }

    private int promptChoice() {
        System.out.print("Choice: ");
        return readInt();
    }

    private void viewMyPlaylists() {
        printHeader("View My Playlists");

        printPlaylists(
                playlistController.handleViewPlaylistsByUser(userId)
        );
    }

    private void createPlaylist() {
        printHeader("Create Playlist");

        boolean success = playlistController.handleCreatePlaylist(userId);

        System.out.println(
                success
                        ? "Playlist created successfully."
                        : "Failed to create playlist."
        );
    }

    private void deletePlaylist() {
        printHeader("Delete Playlist");

        System.out.print("Playlist ID to delete: ");
        int id = readInt();

        boolean success = playlistController.handleDeletePlaylist(id);

        System.out.println(
                success
                        ? "Playlist deleted successfully."
                        : "Failed to delete playlist."
        );
    }

    private void viewSongsInPlaylist() {
        printHeader("View Songs in Playlist");

        System.out.print("Playlist ID: ");
        int playlistId = readInt();

        List<Song> songs =
                playlistController.handleViewSongsInPlaylist(playlistId);

        printSongs(songs);
    }

    private void addSongToPlaylist() {
        printHeader("Add Song to Playlist");

        System.out.print("Playlist ID: ");
        int playlistId = readInt();

        System.out.print("Song ID to add: ");
        int songId = readInt();

        boolean success =
                playlistController.handleAddSongToPlaylist(
                        playlistId,
                        songId
                );

        System.out.println(
                success
                        ? "Song added to playlist."
                        : "Failed to add song to playlist."
        );
    }

    private void removeSongFromPlaylist() {
        printHeader("Remove Song from Playlist");

        System.out.print("Playlist ID: ");
        int playlistId = readInt();

        System.out.print("Song ID to remove: ");
        int songId = readInt();

        boolean success =
                playlistController.handleRemoveSongFromPlaylist(
                        playlistId,
                        songId
                );

        System.out.println(
                success
                        ? "Song removed from playlist."
                        : "Failed to remove song from playlist."
        );
    }

    private void printPlaylists(List<Playlist> playlists) {

        if (playlists.isEmpty()) {
            System.out.println("No playlists found.");
            return;
        }

        String border =
                "+" + "-".repeat(6)
                        + "+" + "-".repeat(16)
                        + "+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-14s |%n",
                "ID",
                "Date Created"
        );

        System.out.println(border);

        for (Playlist playlist : playlists) {

            System.out.printf(
                    "| %-4d | %-14s |%n",
                    playlist.getId(),
                    playlist.getDateCreated()
            );
        }

        System.out.println(border);
    }

    private void printSongs(List<Song> songs) {

        if (songs.isEmpty()) {
            System.out.println("No songs found.");
            return;
        }

        String border =
                "+" + "-".repeat(6)
                        + "+" + "-".repeat(27)
                        + "+" + "-".repeat(10)
                        + "+" + "-".repeat(14)
                        + "+" + "-".repeat(17)
                        + "+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-25s | %-8s | %-12s | %-15s |%n",
                "ID",
                "Title",
                "Length",
                "Genre",
                "Album"
        );

        System.out.println(border);

        for (Song song : songs) {

            System.out.printf(
                    "| %-4d | %-25s | %-8s | %-12s | %-15s |%n",
                    song.getId(),
                    song.getTitle(),
                    song.getLength(),
                    song.getGenre(),
                    song.getAlbumName()
            );
        }

        System.out.println(border);
    }

    // Clears the IntelliJ console using ANSI escape codes
    private void printHeader(String title) {
        clearScreen();

        System.out.println("\n----- " + title + " -----");
    }

    // Fixed version:
    // Does not use ProcessBuilder("clear"),
    // so it will not produce "TERM environment variable not set."
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Reads an int safely
    private int readInt() {

        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }

        int value = scanner.nextInt();

        // Consume leftover newline
        scanner.nextLine();

        return value;
    }
}