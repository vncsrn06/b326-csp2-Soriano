package com.joysistvi.recordingapp.dao;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private final DbConnection dbConnection;

    public UserDao(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public User login(String username, String password) {

        String sql = """
                SELECT * FROM users
                WHERE username = ? AND password = ?
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                );
            }

        } catch (SQLException e) {

            System.out.println("Login Error: " + e.getMessage());
        }

        return null;
    }

    public boolean register(User user) {

        String sql = """
                INSERT INTO users (username, password)
                VALUES (?, ?)
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println("Registration Error: " + e.getMessage());

            return false;
        }
    }
}