package com.database.parking.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.database.parking.enums.Role;
import com.database.parking.models.User;

@Repository

public class UserDAO {
    private final String url = "jdbc:mysql://localhost:3306/parking_management_system";
    private final String dbUserName = "admin";
    private final String dbPassword = "admin";
    
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, dbUserName, dbPassword)) {
            String query = "SELECT * FROM user";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .phone(resultSet.getString("phone"))
                        .role(Role.valueOf(resultSet.getString("role").toUpperCase()))
                        .password(resultSet.getString("password"))
                        .build();
                users.add(user);
            }
        } 
        return users;
    }

    public User getById(long id) throws SQLException {
        User user = null;
        try (Connection connection = DriverManager.getConnection(url, dbUserName, dbPassword)) {
            String query = "SELECT * FROM user WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .phone(resultSet.getString("phone"))
                        .role(Role.valueOf(resultSet.getString("role").toUpperCase()))
                        .password(resultSet.getString("password"))
                        .build();
            }
        } 
        return user;
    }
    
    public User getByNameAndPassword(String name, String password) throws SQLException {
        User user = null;
        try (Connection connection = DriverManager.getConnection(url, dbUserName, dbPassword)) {
            String query = "SELECT * FROM user WHERE name = ? AND password = ?";
            String hashedPassword = User.hashPassword(password);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, hashedPassword);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .phone(resultSet.getString("phone"))
                        .role(Role.valueOf(resultSet.getString("role").toUpperCase()))
                        .password(password)
                        .build();
            }
        } 
        return user;
    }

    public void save(User user) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, dbUserName, dbPassword)) {
            String query = "INSERT INTO user (name, phone, role, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPhone());
            statement.setString(3, user.getRole().toString());
            statement.setString(4, user.getPassword());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(User user) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, dbUserName, dbPassword)) {
            String query = "UPDATE user SET name = ?, phone = ?, role = ?, password = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPhone());
            statement.setString(3, user.getRole().toString());
            statement.setString(4, user.getPassword());
            statement.setLong(5, user.getId());
            statement.executeUpdate();
        } 
    }
    

    public void delete(long id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, dbUserName, dbPassword)) {
            String query = "DELETE FROM user WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } 
    }


    // public static void main(String[] args) {
    //     UserDAO userDAO = new UserDAO();
    //     User user = User.builder()
    //             .name("John mddmmdDoe")
    //             .phone("123hdhdh4567890")
    //             .role((Role.ADMIN))
    //             .password("jfjdjfjfj")
    //             .build();
    //     userDAO.save(user);
    //     System.out.println(userDAO.getAll());
    //     user.setName("Jane Doe");
    //     userDAO.update(user);
    //     System.out.println(userDAO.getAll());
    //     userDAO.delete(user.getId());
    //     System.out.println(userDAO.getAll());
    // }

}
