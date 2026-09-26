package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.model.Song;

import java.util.List;
import java.util.Scanner;

public class SongView {

    private final SongController songController;
    private final Scanner scanner;

    public SongView(
            SongController songController,
            Scanner scanner) {

        this.songController = songController;
        this.scanner = scanner;
    }

    public void viewAlbumSongs(
            int albumId,
            String albumName) {

        System.out.println(
                "\n----- " +
                        albumName +
                        " -----"
        );

        List<Song> songs =
                songController.handleViewSongsByAlbum(
                        albumId
                );

        printSongs(songs);
    }

    public void run() {

        int choice;

        do {

            printMenu();

            choice = promptChoice();

            switch (choice) {

                case 1 -> viewAllSongs();

                case 2 -> searchSong();

                case 3 -> addSong();

                case 4 -> updateSong();

                case 5 -> deleteSong();

                case 0 ->
                        System.out.println(
                                "Returning to main menu..."
                        );

                default ->
                        System.out.println(
                                "Invalid choice. Try again."
                        );
            }

            if (choice != 0) {

                System.out.println(
                        "\nPress Enter to continue..."
                );

                scanner.nextLine();
            }

        } while (choice != 0);
    }

    private void printMenu() {

        System.out.println(
                "\n----- Song Management -----"
        );

        System.out.println(
                "1. View All Songs"
        );

        System.out.println(
                "2. Search Song"
        );

        System.out.println(
                "3. Add Song"
        );

        System.out.println(
                "4. Update Song"
        );

        System.out.println(
                "5. Delete Song"
        );

        System.out.println(
                "0. Back"
        );
    }

    private int promptChoice() {

        System.out.print("Choice: ");

        return readInt();
    }

    private int readInt() {

        while (true) {

            String input =
                    scanner.nextLine();

            try {

                return Integer.parseInt(
                        input.trim()
                );

            } catch (RuntimeException e) {

                System.out.print(
                        "Please enter a valid number: "
                );
            }
        }
    }

    private void viewAllSongs() {

        System.out.println(
                "\n----- All Songs -----"
        );

        List<Song> songs =
                songController.handleViewAllSongs();

        printSongs(songs);
    }

    private void searchSong() {

        System.out.println(
                "\n----- Search Song -----"
        );

        System.out.print(
                "Enter song title: "
        );

        String keyword =
                scanner.nextLine();

        List<Song> songs =
                songController.searchSong(
                        keyword
                );

        printSongs(songs);
    }

    private void addSong() {

        System.out.println(
                "\n----- Add Song -----"
        );

        System.out.print(
                "Title: "
        );

        String title =
                scanner.nextLine();

        if (title.trim().isEmpty()) {

            System.out.println(
                    "Song title cannot be empty."
            );

            return;
        }

        System.out.print(
                "Length (e.g. 3:24): "
        );

        String length =
                scanner.nextLine();

        if (length.trim().isEmpty()) {

            System.out.println(
                    "Length cannot be empty."
            );

            return;
        }

        System.out.print(
                "Genre: "
        );

        String genre =
                scanner.nextLine();

        if (genre.trim().isEmpty()) {

            System.out.println(
                    "Genre cannot be empty."
            );

            return;
        }

        System.out.print(
                "Album ID: "
        );

        int albumId =
                readInt();

        Song song =
                new Song(
                        title,
                        length,
                        genre,
                        albumId
                );

        boolean success =
                songController.handleCreateSong(
                        song
                );

        System.out.println(
                success
                        ? "Song added successfully."
                        : "Failed to add song."
        );
    }

    private void updateSong() {

        System.out.println(
                "\n----- Update Song -----"
        );

        viewAllSongs();

        System.out.print(
                "Song ID to update: "
        );

        int id =
                readInt();

        Song current =
                songController.handleGetSongById(
                        id
                );

        if (current == null) {

            System.out.println(
                    "No song found with ID "
                            + id
                            + "."
            );

            return;
        }

        System.out.print(
                "New title [" +
                        current.getTitle() +
                        "] (press Enter to keep current): "
        );

        String title =
                scanner.nextLine();

        if (title.trim().isEmpty()) {

            title =
                    current.getTitle();
        }

        System.out.print(
                "New length [" +
                        current.getLength() +
                        "] (press Enter to keep current): "
        );

        String length =
                scanner.nextLine();

        if (length.trim().isEmpty()) {

            length =
                    current.getLength();
        }

        System.out.print(
                "New genre [" +
                        current.getGenre() +
                        "] (press Enter to keep current): "
        );

        String genre =
                scanner.nextLine();

        if (genre.trim().isEmpty()) {

            genre =
                    current.getGenre();
        }

        System.out.print(
                "New Album ID [" +
                        current.getAlbumId() +
                        "] (press Enter to keep current): "
        );

        String albumInput =
                scanner.nextLine();

        int albumId;

        if (albumInput.trim().isEmpty()) {

            albumId =
                    current.getAlbumId();

        } else {

            try {

                albumId =
                        Integer.parseInt(
                                albumInput.trim()
                        );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid Album ID."
                );

                return;
            }
        }

        Song song =
                new Song(
                        id,
                        title,
                        length,
                        genre,
                        albumId
                );

        boolean success =
                songController.handleUpdateSong(
                        song
                );

        System.out.println(
                success
                        ? "Song updated successfully."
                        : "Failed to update song."
        );
    }

    private void deleteSong() {

        System.out.println(
                "\n----- Delete Song -----"
        );

        viewAllSongs();

        System.out.print(
                "Song ID to delete: "
        );

        int id =
                readInt();

        Song current =
                songController.handleGetSongById(
                        id
                );

        if (current == null) {

            System.out.println(
                    "No song found with ID "
                            + id
                            + "."
            );

            return;
        }

        System.out.println(
                "Song: " +
                        current.getTitle()
        );

        System.out.print(
                "Are you sure you want to delete this song? (Y/N): "
        );

        String confirm =
                scanner.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {

            boolean success =
                    songController.handleDeleteSong(
                            id
                    );

            System.out.println(
                    success
                            ? "Song deleted successfully."
                            : "Failed to delete song."
            );

        } else {

            System.out.println(
                    "Delete cancelled."
            );
        }
    }

    private void printSongs(List<Song> songs) {

        if (songs == null ||
                songs.isEmpty()) {

            System.out.println(
                    "No songs found for this album."
            );

            return;
        }

        String border =
                "+" +
                        "-".repeat(6) +
                        "+" +
                        "-".repeat(27) +
                        "+" +
                        "-".repeat(10) +
                        "+" +
                        "-".repeat(17) +
                        "+" +
                        "-".repeat(10) +
                        "+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-25s | %-8s | %-15s | %-8s |%n",
                "ID",
                "Title",
                "Length",
                "Genre",
                "Album ID"
        );

        System.out.println(border);

        for (Song song : songs) {

            System.out.printf(
                    "| %-4d | %-25s | %-8s | %-15s | %-8d |%n",
                    song.getId(),
                    song.getTitle(),
                    song.getLength(),
                    song.getGenre(),
                    song.getAlbumId()
            );
        }

        System.out.println(border);
    }
}

