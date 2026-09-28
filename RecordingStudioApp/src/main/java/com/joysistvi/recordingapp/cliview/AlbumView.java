package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.model.Album;

import java.util.List;
import java.util.Scanner;

public class AlbumView {

    private final AlbumController albumController;
    private final Scanner scanner;

    public AlbumView(
            AlbumController albumController,
            Scanner scanner) {

        this.albumController = albumController;
        this.scanner = scanner;
    }

    public void run() {

        int choice;

        do {

            printMenu();

            choice = readInt("Choice: ");

            switch (choice) {

                case 1 -> viewAllAlbums();

                case 2 -> searchAlbum();

                case 3 -> addAlbum();

                case 4 -> updateAlbum();

                case 5 -> deleteAlbum();

                case 6 -> viewArchivedAlbums();

                case 7 -> restoreAlbum();

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

        System.out.println();
        System.out.println(
                "----- Album Management -----"
        );

        System.out.println(
                "1. View All Albums"
        );

        System.out.println(
                "2. Search Album"
        );

        System.out.println(
                "3. Add Album"
        );

        System.out.println(
                "4. Update Album"
        );

        System.out.println(
                "5. Delete Album"
        );

        System.out.println(
                "6. View Archived Albums"
        );

        System.out.println(
                "7. Restore Album"
        );

        System.out.println(
                "0. Back"
        );
    }

    private void viewAllAlbums() {

        System.out.println();
        System.out.println(
                "----- All Albums -----"
        );

        List<Album> albums =
                albumController.handleViewAllAlbums();

        printAlbums(albums);
    }

    private void searchAlbum() {

        System.out.println();
        System.out.println(
                "----- Search Album -----"
        );

        System.out.print(
                "Enter album name: "
        );

        String keyword =
                scanner.nextLine();

        List<Album> albums =
                albumController.searchAlbum(
                        keyword
                );

        printAlbums(albums);
    }

    private void addAlbum() {

        System.out.println();
        System.out.println(
                "----- Add Album -----"
        );

        System.out.print(
                "Album name: "
        );

        String name =
                scanner.nextLine();

        if (name.trim().isEmpty()) {

            System.out.println(
                    "Album name cannot be empty."
            );

            return;
        }

        int year =
                readInt("Year: ");

        int artistId =
                readInt("Artist ID: ");

        Album album =
                new Album(
                        name,
                        year,
                        artistId
                );

        boolean success =
                albumController.handleCreateAlbum(
                        album
                );

        System.out.println(
                success
                        ? "Album added successfully."
                        : "Failed to add album."
        );
    }

    private void updateAlbum() {

        System.out.println();
        System.out.println(
                "----- Update Album -----"
        );

        viewAllAlbums();

        int id =
                readInt("Album ID to update: ");

        Album current =
                albumController.handleGetAlbumById(
                        id
                );

        if (current == null) {

            System.out.println(
                    "No album found with ID " + id + "."
            );

            return;
        }

        System.out.print(
                "New name [" +
                        current.getName() +
                        "] (Enter to keep): "
        );

        String name =
                scanner.nextLine();

        if (name.trim().isEmpty()) {

            name =
                    current.getName();
        }

        System.out.print(
                "New year [" +
                        current.getYear() +
                        "] (Enter to keep): "
        );

        String yearInput =
                scanner.nextLine();

        int year;

        if (yearInput.trim().isEmpty()) {

            year =
                    current.getYear();

        } else {

            try {

                year =
                        Integer.parseInt(
                                yearInput.trim()
                        );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid year."
                );

                return;
            }
        }

        System.out.print(
                "New Artist ID [" +
                        current.getArtistId() +
                        "] (Enter to keep): "
        );

        String artistInput =
                scanner.nextLine();

        int artistId;

        if (artistInput.trim().isEmpty()) {

            artistId =
                    current.getArtistId();

        } else {

            try {

                artistId =
                        Integer.parseInt(
                                artistInput.trim()
                        );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid Artist ID."
                );

                return;
            }
        }

        Album album =
                new Album(
                        id,
                        name,
                        year,
                        artistId
                );

        boolean success =
                albumController.handleUpdateAlbum(
                        album
                );

        System.out.println(
                success
                        ? "Album updated successfully."
                        : "Failed to update album."
        );
    }

    private void deleteAlbum() {

        System.out.println();
        System.out.println(
                "----- Delete Album -----"
        );

        viewAllAlbums();

        int id =
                readInt("Album ID to archive: ");

        Album album =
                albumController.handleGetAlbumById(
                        id
                );

        if (album == null) {

            System.out.println(
                    "No album found with ID " + id + "."
            );

            return;
        }

        System.out.println(
                "Album: " +
                        album.getName()
        );

        System.out.print(
                "Archive this album? (Y/N): "
        );

        String confirm =
                scanner.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {

            boolean success =
                    albumController.handleArchiveAlbum(
                            id
                    );

            System.out.println(
                    success
                            ? "Album archived successfully."
                            : "Failed to archive album."
            );

        } else {

            System.out.println(
                    "Archive cancelled."
            );
        }
    }

    private void viewArchivedAlbums() {

        System.out.println();
        System.out.println(
                "----- Archived Albums -----"
        );

        List<Album> albums =
                albumController.handleViewArchivedAlbums();

        printAlbums(albums);
    }

    private void restoreAlbum() {

        System.out.println();
        System.out.println(
                "----- Restore Album -----"
        );

        viewArchivedAlbums();

        int id =
                readInt("Album ID to restore: ");

        boolean success =
                albumController.handleRestoreAlbum(
                        id
                );

        System.out.println(
                success
                        ? "Album restored successfully."
                        : "Failed to restore album."
        );
    }

    private void printAlbums(List<Album> albums) {

        if (albums == null ||
                albums.isEmpty()) {

            System.out.println(
                    "No albums found."
            );

            return;
        }

        String border =
                "+" +
                        "-".repeat(6) +
                        "+" +
                        "-".repeat(27) +
                        "+" +
                        "-".repeat(8) +
                        "+" +
                        "-".repeat(11) +
                        "+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-25s | %-6s | %-9s |%n",
                "ID",
                "Album",
                "Year",
                "Artist ID"
        );

        System.out.println(border);

        for (Album album : albums) {

            System.out.printf(
                    "| %-4d | %-25s | %-6d | %-9d |%n",
                    album.getId(),
                    album.getName(),
                    album.getYear(),
                    album.getArtistId()
            );
        }

        System.out.println(border);
    }

    private int readInt(String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine();

            try {

                return Integer.parseInt(
                        input.trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}
