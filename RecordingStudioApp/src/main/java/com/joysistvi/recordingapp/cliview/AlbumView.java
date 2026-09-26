package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.model.Album;

import java.util.List;
import java.util.Scanner;

public class AlbumView {

    private final AlbumController albumController;
    private final Scanner scanner;

    public AlbumView(AlbumController albumController, Scanner scanner) {
        this.albumController = albumController;
        this.scanner = scanner;
    }

    public void run() {

        int choice;

        do {
            printMenu();

            choice = promptChoice();

            switch (choice) {
                case 1 -> viewAllAlbums();
                case 2 -> searchAlbum();
                case 3 -> addAlbum();
                case 4 -> updateAlbum();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }

            if (choice != 0) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }

        } while (choice != 0);
    }

    private void printMenu() {

        System.out.println("\n----- Album Management -----");
        System.out.println("1. View All Albums");
        System.out.println("2. Search Album");
        System.out.println("3. Add Album");
        System.out.println("4. Update Album");
        System.out.println("0. Back");
    }

    private int promptChoice() {

        System.out.print("Choice: ");

        return readInt();
    }

    private int readInt() {

        while (true) {

            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input.trim());

            } catch (RuntimeException e) {

                System.out.print(
                        "Please enter a valid number: "
                );
            }
        }
    }

    private void viewAllAlbums() {

        System.out.println("\n----- View All Albums -----");

        List<Album> albums =
                albumController.handleViewAllAlbums();

        printAlbums(albums);
    }

    private void searchAlbum() {

        System.out.println("\n----- Search Album -----");

        System.out.print("Enter album name: ");

        String keyword = scanner.nextLine();

        List<Album> albums =
                albumController.searchAlbum(keyword);

        printAlbums(albums);
    }

    private void addAlbum() {

        System.out.println("\n----- Add Album -----");

        System.out.print("Album Name: ");
        String name = scanner.nextLine();

        if (name.trim().isEmpty()) {

            System.out.println(
                    "Album name cannot be empty."
            );

            return;
        }

        System.out.print("Year: ");
        int year = readInt();

        System.out.print("Artist ID: ");
        int artistId = readInt();

        Album album =
                new Album(name, year, artistId);

        boolean isSuccess =
                albumController.handleCreateAlbum(album);

        if (isSuccess) {

            System.out.println(
                    "Album added successfully."
            );

            System.out.println();

            viewAllAlbums();

        } else {

            System.out.println(
                    "Failed to add album."
            );
        }
    }

    private void updateAlbum() {

        System.out.println("\n----- Update Album -----");

        viewAllAlbums();

        System.out.print("Album ID to update: ");

        int id = readInt();

        Album current =
                albumController.handleGetAlbumById(id);

        if (current == null) {

            System.out.println(
                    "No album found with ID " + id + "."
            );

            return;
        }

        System.out.print(
                "New Name [" + current.getName() +
                        "] (press Enter to keep current): "
        );

        String name = scanner.nextLine();

        if (name.trim().isEmpty()) {

            name = current.getName();
        }

        System.out.print(
                "New Year [" + current.getYear() +
                        "] (enter 0 to keep current): "
        );

        String yearInput = scanner.nextLine();

        int year;

        if (yearInput.trim().isEmpty()
                || yearInput.trim().equals("0")) {

            year = current.getYear();

        } else {

            try {

                year = Integer.parseInt(
                        yearInput.trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid year. Update cancelled."
                );

                return;
            }
        }

        System.out.print(
                "New Artist ID [" + current.getArtistId() +
                        "] (enter 0 to keep current): "
        );

        String artistInput = scanner.nextLine();

        int artistId;

        if (artistInput.trim().isEmpty()
                || artistInput.trim().equals("0")) {

            artistId = current.getArtistId();

        } else {

            try {

                artistId = Integer.parseInt(
                        artistInput.trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid Artist ID. Update cancelled."
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

        boolean isSuccess =
                albumController.handleUpdateAlbum(album);

        if (isSuccess) {

            System.out.println(
                    "Album updated successfully."
            );

            System.out.println();

            viewAllAlbums();

        } else {

            System.out.println(
                    "Failed to update album."
            );
        }
    }

    public void printAlbums(List<Album> albums) {

        if (albums == null || albums.isEmpty()) {

            System.out.println("No albums found.");

            return;
        }

        String border =
                "+" + "-".repeat(6) +
                        "+" + "-".repeat(27) +
                        "+" + "-".repeat(8) +
                        "+" + "-".repeat(12) +
                        "+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-25s | %-6s | %-10s |%n",
                "ID",
                "Name",
                "Year",
                "Artist ID"
        );

        System.out.println(border);

        for (Album album : albums) {

            System.out.printf(
                    "| %-4d | %-25s | %-6d | %-10d |%n",
                    album.getId(),
                    album.getName(),
                    album.getYear(),
                    album.getArtistId()
            );
        }

        System.out.println(border);
    }
}
