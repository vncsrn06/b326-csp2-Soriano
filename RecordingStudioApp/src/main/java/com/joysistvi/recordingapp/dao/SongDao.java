package com.joysistvi.recordingapp.dao;

import com.joysistvi.recordingapp.config.DbConnection;

import java.sql.*;

public class SongDao {

    private final DbConnection dbConnection;

    public SongDao(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // =========================
    // READ ALL SONGS
    // =========================

    public void readAllSongs() {

        String query = "SELECT * FROM songs";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            System.out.println(
                    "+-------+-------------------------+----------+-----------------+----------+"
            );

            System.out.printf(
                    "| %-5s | %-23s | %-8s | %-15s | %-8s |%n",
                    "ID",
                    "Title",
                    "Length",
                    "Genre",
                    "Album ID"
            );

            System.out.println(
                    "+-------+-------------------------+----------+-----------------+----------+"
            );

            while (result.next()) {

                int id = result.getInt("id");

                String title =
                        result.getString("title");

                String length =
                        result.getString("length");

                String genre =
                        result.getString("genre");

                int albumId =
                        result.getInt("album_id");

                System.out.printf(
                        "| %-5d | %-23s | %-8s | %-15s | %-8d |%n",
                        id,
                        title,
                        length,
                        genre,
                        albumId
                );
            }

            System.out.println(
                    "+-------+-------------------------+----------+-----------------+----------+"
            );

        } catch (SQLException e) {

            System.err.println(
                    "Get All Songs: " + e.getMessage()
            );
        }
    }


    // =========================
    // READ SONGS BY ALBUM ID
    // =========================

    public void readSongsByAlbumId(int albumId) {

        if (albumId <= 0) {

            System.out.println(
                    "Invalid album ID."
            );

            return;
        }

        String query =
                "SELECT * FROM songs WHERE album_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setInt(1, albumId);

            ResultSet result =
                    prep.executeQuery();

            System.out.println(
                    "+-------+-------------------------+----------+-----------------+----------+"
            );

            System.out.printf(
                    "| %-5s | %-23s | %-8s | %-15s | %-8s |%n",
                    "ID",
                    "Title",
                    "Length",
                    "Genre",
                    "Album ID"
            );

            System.out.println(
                    "+-------+-------------------------+----------+-----------------+----------+"
            );

            while (result.next()) {

                System.out.printf(
                        "| %-5d | %-23s | %-8s | %-15s | %-8d |%n",
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("length"),
                        result.getString("genre"),
                        result.getInt("album_id")
                );
            }

            System.out.println(
                    "+-------+-------------------------+----------+-----------------+----------+"
            );

        } catch (SQLException e) {

            System.err.println(
                    "Get Songs By Album ID: "
                            + e.getMessage()
            );
        }
    }


    // =========================
    // CREATE SONG
    // =========================

    public void createSong(
            String title,
            String length,
            String genre,
            int albumId) {

        if (title == null ||
                title.trim().isEmpty()) {

            System.out.println(
                    "Song title is required."
            );

            return;
        }

        if (length == null ||
                length.trim().isEmpty()) {

            System.out.println(
                    "Song length is required."
            );

            return;
        }

        if (genre == null ||
                genre.trim().isEmpty()) {

            System.out.println(
                    "Song genre is required."
            );

            return;
        }

        if (albumId <= 0) {

            System.out.println(
                    "Invalid album ID."
            );

            return;
        }

        String query =
                "INSERT INTO songs " +
                        "(title, length, genre, album_id) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setString(1, title);

            // length is String because values are like 3:24
            prep.setString(2, length);

            prep.setString(3, genre);

            prep.setInt(4, albumId);

            int rows =
                    prep.executeUpdate();

            System.out.println(
                    rows > 0
                            ? "Song " + title +
                            " added successfully.\n"
                            : "Failed to add song."
            );

            readAllSongs();

        } catch (SQLException e) {

            System.err.println(
                    "Create Song: "
                            + e.getMessage()
            );
        }
    }


    // =========================
    // UPDATE SONG
    // =========================

    public void updateSong(
            String title,
            String length,
            String genre,
            int albumId,
            int id) {

        if (id <= 0) {

            System.out.println(
                    "Invalid song ID."
            );

            return;
        }

        if (title == null ||
                title.trim().isEmpty()) {

            System.out.println(
                    "Song title is required."
            );

            return;
        }

        if (length == null ||
                length.trim().isEmpty()) {

            System.out.println(
                    "Song length is required."
            );

            return;
        }

        if (genre == null ||
                genre.trim().isEmpty()) {

            System.out.println(
                    "Song genre is required."
            );

            return;
        }

        if (albumId <= 0) {

            System.out.println(
                    "Invalid album ID."
            );

            return;
        }

        String query =
                "UPDATE songs SET " +
                        "title = ?, " +
                        "length = ?, " +
                        "genre = ?, " +
                        "album_id = ? " +
                        "WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setString(1, title);

            // length is String
            prep.setString(2, length);

            prep.setString(3, genre);

            prep.setInt(4, albumId);

            prep.setInt(5, id);

            int rows =
                    prep.executeUpdate();

            System.out.println(
                    rows > 0
                            ? "Song " + title +
                            " updated successfully."
                            : "Failed to update song."
            );

            System.out.println();

            readAllSongs();

        } catch (SQLException e) {

            System.out.println(
                    "Update Song: "
                            + e.getMessage()
            );
        }
    }


    // =========================
    // DELETE SONG
    // =========================

    public void deleteSong(int id) {

        if (id <= 0) {

            System.out.println(
                    "Invalid song ID."
            );

            return;
        }

        String query =
                "DELETE FROM songs WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rows =
                    prep.executeUpdate();

            System.out.println(
                    rows > 0
                            ? "Song " + id +
                            " deleted successfully.\n"
                            : "Failed to delete song."
            );

            readAllSongs();

        } catch (SQLException e) {

            System.err.println(
                    "Delete Song: "
                            + e.getMessage()
            );
        }
    }


    // =========================
    // READ SONG BY ID
    // =========================

    public void readSongById(int id) {

        if (id <= 0) {

            System.out.println(
                    "Invalid song ID."
            );

            return;
        }

        String query =
                "SELECT * FROM songs WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setInt(1, id);

            ResultSet res =
                    prep.executeQuery();

            System.out.println(
                    "+-------+-------------------------+----------+-----------------+----------+"
            );

            System.out.printf(
                    "| %-5s | %-23s | %-8s | %-15s | %-8s |%n",
                    "ID",
                    "Title",
                    "Length",
                    "Genre",
                    "Album ID"
            );

            System.out.println(
                    "+-------+-------------------------+----------+-----------------+----------+"
            );

            if (res.next()) {

                System.out.printf(
                        "| %-5d | %-23s | %-8s | %-15s | %-8d |%n",
                        res.getInt("id"),
                        res.getString("title"),
                        res.getString("length"),
                        res.getString("genre"),
                        res.getInt("album_id")
                );
            }

            System.out.println(
                    "+-------+-------------------------+----------+-----------------+----------+"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Read Song By Id: "
                            + e.getMessage()
            );
        }
    }


    // =========================
    // SEARCH SONG
    // =========================

    public void searchSong(String keyword) {

        if (keyword == null ||
                keyword.trim().isEmpty()) {

            System.out.println(
                    "Search keyword cannot be empty."
            );

            return;
        }

        String query =
                "SELECT * FROM songs " +
                        "WHERE title LIKE ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setString(
                    1,
                    "%" + keyword.trim() + "%"
            );

            ResultSet res =
                    prep.executeQuery();

            System.out.println(
                    "+-------+-------------------------+----------+-----------------+----------+"
            );

            System.out.printf(
                    "| %-5s | %-23s | %-8s | %-15s | %-8s |%n",
                    "ID",
                    "Title",
                    "Length",
                    "Genre",
                    "Album ID"
            );

            System.out.println(
                    "+-------+-------------------------+----------+-----------------+----------+"
            );

            while (res.next()) {

                System.out.printf(
                        "| %-5d | %-23s | %-8s | %-15s | %-8d |%n",
                        res.getInt("id"),
                        res.getString("title"),
                        res.getString("length"),
                        res.getString("genre"),
                        res.getInt("album_id")
                );
            }

            System.out.println(
                    "+-------+-------------------------+----------+-----------------+----------+"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Search Song: "
                            + e.getMessage()
            );
        }
    }
}

