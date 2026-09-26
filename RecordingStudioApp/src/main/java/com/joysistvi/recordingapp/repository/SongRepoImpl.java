package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongRepoImpl implements SongRepo {

    private final DbConnection dbConnection;

    public SongRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Song> getAllSongs() {

        List<Song> songs = new ArrayList<>();

        String query = "SELECT * FROM songs";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {

                songs.add(new Song(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("length"),
                        result.getString("genre"),
                        result.getInt("album_id")
                ));
            }

        } catch (SQLException e) {

            System.err.println(
                    "Get All Songs Error: "
                            + e.getMessage()
            );
        }

        return songs;
    }

    @Override
    public List<Song> getSongsByAlbumId(int albumId) {

        List<Song> songs = new ArrayList<>();

        String query =
                "SELECT * FROM songs WHERE album_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setInt(1, albumId);

            try (ResultSet result = prep.executeQuery()) {

                while (result.next()) {

                    songs.add(new Song(
                            result.getInt("id"),
                            result.getString("title"),
                            result.getString("length"),
                            result.getString("genre"),
                            result.getInt("album_id")
                    ));
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Get Songs By Album ID Error: "
                            + e.getMessage()
            );
        }

        return songs;
    }

    @Override
    public Song getSongById(int id) {

        String query =
                "SELECT * FROM songs WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet result = prep.executeQuery()) {

                if (result.next()) {

                    return new Song(
                            result.getInt("id"),
                            result.getString("title"),
                            result.getString("length"),
                            result.getString("genre"),
                            result.getInt("album_id")
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Get Song By ID Error: "
                            + e.getMessage()
            );
        }

        return null;
    }

    @Override
    public List<Song> searchSong(String keyword) {

        List<Song> songs = new ArrayList<>();

        String query =
                "SELECT * FROM songs WHERE title LIKE ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setString(
                    1,
                    "%" + keyword + "%"
            );

            try (ResultSet result = prep.executeQuery()) {

                while (result.next()) {

                    songs.add(new Song(
                            result.getInt("id"),
                            result.getString("title"),
                            result.getString("length"),
                            result.getString("genre"),
                            result.getInt("album_id")
                    ));
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Search Song Error: "
                            + e.getMessage()
            );
        }

        return songs;
    }

    @Override
    public boolean createSong(Song song) {

        String query =
                "INSERT INTO songs " +
                        "(title, length, genre, album_id) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setString(
                    1,
                    song.getTitle()
            );

            prep.setString(
                    2,
                    song.getLength()
            );

            prep.setString(
                    3,
                    song.getGenre()
            );

            prep.setInt(
                    4,
                    song.getAlbumId()
            );

            int rowsAffected =
                    prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Create Song Error: "
                            + e.getMessage()
            );
        }

        return false;
    }

    @Override
    public boolean updateSong(Song song) {

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

            prep.setString(
                    1,
                    song.getTitle()
            );

            prep.setString(
                    2,
                    song.getLength()
            );

            prep.setString(
                    3,
                    song.getGenre()
            );

            prep.setInt(
                    4,
                    song.getAlbumId()
            );

            prep.setInt(
                    5,
                    song.getId()
            );

            int rowsAffected =
                    prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Update Song Error: "
                            + e.getMessage()
            );
        }

        return false;
    }

    @Override
    public boolean deleteSong(int id) {

        String query =
                "DELETE FROM songs WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep =
                     conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected =
                    prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Delete Song Error: "
                            + e.getMessage()
            );
        }

        return false;
    }
}
