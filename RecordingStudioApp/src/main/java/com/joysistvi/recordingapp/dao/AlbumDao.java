package com.joysistvi.recordingapp.dao;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Album;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumDao {

    private final DbConnection dbConnection;

    public AlbumDao(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // =========================
    // GET ALL ALBUMS
    // =========================

    public List<Album> getAllAlbums() {

        List<Album> albums = new ArrayList<>();

        String query =
                "SELECT * FROM albums WHERE is_archived = 0";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {

                Album album = new Album(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("year"),
                        result.getInt("artist_id")
                );

                albums.add(album);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Get All Albums: " + e.getMessage()
            );
        }

        return albums;
    }


    // =========================
    // GET ALBUMS BY ARTIST
    // =========================

    public List<Album> getAlbumsByArtistId(int artistId) {

        List<Album> albums = new ArrayList<>();

        if (artistId <= 0) {
            return albums;
        }

        String query =
                "SELECT * FROM albums " +
                        "WHERE artist_id = ? " +
                        "AND is_archived = 0";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setInt(1, artistId);

            ResultSet result = prep.executeQuery();

            while (result.next()) {

                Album album = new Album(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("year"),
                        result.getInt("artist_id")
                );

                albums.add(album);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Get Albums By Artist ID: "
                            + e.getMessage()
            );
        }

        return albums;
    }


    // =========================
    // GET ALBUM BY ID
    // =========================

    public Album getAlbumById(int id) {

        if (id <= 0) {
            return null;
        }

        String query =
                "SELECT * FROM albums WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setInt(1, id);

            ResultSet result =
                    prep.executeQuery();

            if (result.next()) {

                return new Album(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("year"),
                        result.getInt("artist_id")
                );
            }

        } catch (SQLException e) {

            System.err.println(
                    "Get Album By ID: "
                            + e.getMessage()
            );
        }

        return null;
    }


    // =========================
    // SEARCH ALBUM
    // =========================

    public List<Album> searchAlbum(String keyword) {

        List<Album> albums = new ArrayList<>();

        if (keyword == null ||
                keyword.trim().isEmpty()) {

            return albums;
        }

        String query =
                "SELECT * FROM albums " +
                        "WHERE name LIKE ? " +
                        "AND is_archived = 0";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setString(
                    1,
                    "%" + keyword.trim() + "%"
            );

            ResultSet result =
                    prep.executeQuery();

            while (result.next()) {

                Album album = new Album(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("year"),
                        result.getInt("artist_id")
                );

                albums.add(album);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Search Album: "
                            + e.getMessage()
            );
        }

        return albums;
    }


    // =========================
    // GET ARCHIVED ALBUMS
    // =========================

    public List<Album> getAllArchivedAlbums() {

        List<Album> albums = new ArrayList<>();

        String query =
                "SELECT * FROM albums " +
                        "WHERE is_archived = 1";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result =
                     stmnt.executeQuery(query)) {

            while (result.next()) {

                Album album = new Album(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("year"),
                        result.getInt("artist_id")
                );

                albums.add(album);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Get Archived Albums: "
                            + e.getMessage()
            );
        }

        return albums;
    }


    // =========================
    // CREATE ALBUM
    // =========================

    public boolean createAlbum(Album album) {

        if (album == null) {
            return false;
        }

        if (album.getName() == null ||
                album.getName().trim().isEmpty()) {

            return false;
        }

        if (album.getYear() <= 0 ||
                album.getArtistId() <= 0) {

            return false;
        }

        String query =
                "INSERT INTO albums " +
                        "(name, year, artist_id) " +
                        "VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setString(
                    1,
                    album.getName()
            );

            prep.setInt(
                    2,
                    album.getYear()
            );

            prep.setInt(
                    3,
                    album.getArtistId()
            );

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Create Album: "
                            + e.getMessage()
            );

            return false;
        }
    }


    // =========================
    // UPDATE ALBUM
    // =========================

    public boolean updateAlbum(Album album) {

        if (album == null ||
                album.getId() <= 0) {

            return false;
        }

        if (album.getName() == null ||
                album.getName().trim().isEmpty()) {

            return false;
        }

        if (album.getYear() <= 0 ||
                album.getArtistId() <= 0) {

            return false;
        }

        String query =
                "UPDATE albums SET " +
                        "name = ?, " +
                        "year = ?, " +
                        "artist_id = ? " +
                        "WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setString(
                    1,
                    album.getName()
            );

            prep.setInt(
                    2,
                    album.getYear()
            );

            prep.setInt(
                    3,
                    album.getArtistId()
            );

            prep.setInt(
                    4,
                    album.getId()
            );

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Update Album: "
                            + e.getMessage()
            );

            return false;
        }
    }


    // =========================
    // ARCHIVE ALBUM
    // =========================

    public boolean archiveAlbum(int id) {

        if (id <= 0) {
            return false;
        }

        String query =
                "UPDATE albums " +
                        "SET is_archived = 1 " +
                        "WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setInt(1, id);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Archive Album: "
                            + e.getMessage()
            );

            return false;
        }
    }


    // =========================
    // RESTORE ALBUM
    // =========================

    public boolean restoreAlbum(int id) {

        if (id <= 0) {
            return false;
        }

        String query =
                "UPDATE albums " +
                        "SET is_archived = 0 " +
                        "WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setInt(1, id);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Restore Album: "
                            + e.getMessage()
            );

            return false;
        }
    }


    // =========================
    // DELETE ALBUM
    // =========================

    public boolean deleteAlbum(int id) {

        if (id <= 0) {
            return false;
        }

        String query =
                "DELETE FROM albums WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setInt(1, id);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Delete Album: "
                            + e.getMessage()
            );

            return false;
        }
    }
}
