package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.controller.ArtistController;
import com.joysistvi.recordingapp.controller.SongController;

import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.model.Artist;
import com.joysistvi.recordingapp.model.Song;

import java.util.List;
import java.util.Scanner;

public class ArtistView {

    private final ArtistController artistController;
    private final AlbumController albumController;
    private final SongController songController;
    private final Scanner scanner;

    public ArtistView(
            ArtistController artistController,
            AlbumController albumController,
            SongController songController,
            Scanner scanner) {

        this.artistController = artistController;
        this.albumController = albumController;
        this.songController = songController;
        this.scanner = scanner;
    }

    public void run() {

        int choice;

        do {

            printMenu();

            choice = promptChoice();

            switch (choice) {

                case 1 -> viewAllArtists();

                case 2 -> searchArtist();

                case 3 -> addArtists();

                case 4 -> updateArtist();

                case 5 -> viewArtistAlbums();

                case 6 -> deleteArtist();

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
                "\n----- Artist Management -----"
        );

        System.out.println(
                "1. View All Artists"
        );

        System.out.println(
                "2. Search Artist"
        );

        System.out.println(
                "3. Add Artist"
        );

        System.out.println(
                "4. Update Artist"
        );

        System.out.println(
                "5. View Artist Albums"
        );

        System.out.println(
                "6. Delete Artist"
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

    // ==========================================
    // VIEW ALL ARTISTS
    // ==========================================

    private void viewAllArtists() {

        System.out.println(
                "\n----- View All Artists -----"
        );

        List<Artist> artists =
                artistController.handleViewAllArtist();

        printArtists(artists);
    }

    // ==========================================
    // SEARCH ARTIST
    // ==========================================

    private void searchArtist() {

        System.out.println(
                "\n----- Search Artists -----"
        );

        System.out.print(
                "Enter name: "
        );

        String keyword =
                scanner.nextLine();

        List<Artist> artists =
                artistController.searchArtist(
                        keyword
                );

        printArtists(artists);
    }

    // ==========================================
    // ADD ARTIST
    // ==========================================

    private void addArtists() {

        System.out.println(
                "\n----- Add Artist -----"
        );

        System.out.print(
                "Name: "
        );

        String name =
                scanner.nextLine();

        if (name.trim().isEmpty()) {

            System.out.println(
                    "Artist name cannot be empty."
            );

            return;
        }

        Artist artist =
                new Artist(name);

        boolean success =
                artistController.handleCreateArtist(
                        artist
                );

        System.out.println(
                success
                        ? "Artist added successfully."
                        : "Failed to add artist."
        );

        if (success) {

            System.out.println();

            viewAllArtists();
        }
    }

    // ==========================================
    // UPDATE ARTIST
    // ==========================================

    private void updateArtist() {

        System.out.println(
                "\n----- Update Artist -----"
        );

        viewAllArtists();

        System.out.print(
                "Artist ID to update: "
        );

        int id =
                readInt();

        Artist current =
                artistController.handleGetArtistById(
                        id
                );

        if (current == null) {

            System.out.println(
                    "No artist found with ID "
                            + id
                            + "."
            );

            return;
        }

        System.out.print(
                "New Name [" +
                        current.getName() +
                        "] (press Enter to keep current): "
        );

        String name =
                scanner.nextLine();

        if (name.trim().isEmpty()) {

            name =
                    current.getName();
        }

        Artist artist =
                new Artist(
                        id,
                        name
                );

        boolean success =
                artistController.handleUpdateArtist(
                        artist
                );

        System.out.println(
                success
                        ? "Artist updated successfully."
                        : "Failed to update artist."
        );

        if (success) {

            System.out.println();

            viewAllArtists();
        }
    }

    // ==========================================
    // ARTIST → ALBUM → SONG
    // ==========================================

    private void viewArtistAlbums() {

        System.out.println(
                "\n----- View Artist Albums -----"
        );

        List<Artist> artists =
                artistController.handleViewAllArtist();

        printArtists(artists);

        if (artists.isEmpty()) {

            return;
        }

        System.out.print(
                "\nEnter Artist ID: "
        );

        int artistId =
                readInt();

        Artist artist =
                artistController.handleGetArtistById(
                        artistId
                );

        if (artist == null) {

            System.out.println(
                    "No artist found with ID "
                            + artistId
                            + "."
            );

            return;
        }

        // ------------------------------------------
        // SHOW ALBUMS
        // ------------------------------------------

        System.out.println(
                "\n----- " +
                        artist.getName() +
                        "'s Albums -----"
        );

        List<Album> albums =
                albumController.handleViewAlbumsByArtist(
                        artistId
                );

        if (albums == null || albums.isEmpty()) {

            System.out.println(
                    "No albums found for this artist."
            );

            return;
        }

        printAlbums(albums);

        // ------------------------------------------
        // CHOOSE ALBUM
        // ------------------------------------------

        System.out.print(
                "\nEnter Album ID: "
        );

        int albumId =
                readInt();

        Album selectedAlbum =
                albumController.handleGetAlbumById(
                        albumId
                );

        if (selectedAlbum == null) {

            System.out.println(
                    "No album found with ID "
                            + albumId
                            + "."
            );

            return;
        }

        // Make sure the album belongs to
        // the artist that was selected.

        if (selectedAlbum.getArtistId()
                != artistId) {

            System.out.println(
                    "That album does not belong "
                            + "to this artist."
            );

            return;
        }

        // ------------------------------------------
        // SHOW SONGS
        // ------------------------------------------

        System.out.println(
                "\n----- " +
                        selectedAlbum.getName() +
                        " -----"
        );

        List<Song> songs =
                songController.handleViewSongsByAlbum(
                        albumId
                );

        printSongs(songs);
    }

    // ==========================================
    // DELETE ARTIST
    // ==========================================

    private void deleteArtist() {

        System.out.println(
                "\n----- Delete Artist -----"
        );

        viewAllArtists();

        System.out.print(
                "Artist ID to delete: "
        );

        int id =
                readInt();

        Artist current =
                artistController.handleGetArtistById(
                        id
                );

        if (current == null) {

            System.out.println(
                    "No artist found with ID "
                            + id
                            + "."
            );

            return;
        }

        System.out.println(
                "Artist: " +
                        current.getName()
        );

        System.out.print(
                "Are you sure you want to delete "
                        + "this artist? (Y/N): "
        );

        String confirm =
                scanner.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {

            boolean success =
                    artistController.handleDeleteArtist(
                            id
                    );

            System.out.println(
                    success
                            ? "Artist deleted successfully."
                            : "Failed to delete artist."
            );

            if (success) {

                System.out.println();

                viewAllArtists();
            }

        } else {

            System.out.println(
                    "Delete cancelled."
            );
        }
    }

    // ==========================================
    // PRINT ARTISTS
    // ==========================================

    public void printArtists(
            List<Artist> artists) {

        if (artists == null ||
                artists.isEmpty()) {

            System.out.println(
                    "No artists found."
            );

            return;
        }

        String border =
                "+" +
                        "-".repeat(6) +
                        "+" +
                        "-".repeat(27) +
                        "+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-25s |%n",
                "ID",
                "Name"
        );

        System.out.println(border);

        for (Artist artist : artists) {

            System.out.printf(
                    "| %-4d | %-25s |%n",
                    artist.getId(),
                    artist.getName()
            );
        }

        System.out.println(border);
    }

    // ==========================================
    // PRINT ALBUMS
    // ==========================================

    private void printAlbums(
            List<Album> albums) {

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
                        "-".repeat(12) +
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

    // ==========================================
    // PRINT SONGS
    // ==========================================

    private void printSongs(
            List<Song> songs) {

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
