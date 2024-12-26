package com.database.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.database.parking.models.Notification;

public class NotificationDAO {
    private final String url = "jdbc:mysql://localhost:3306/parking_management_system";
    private final String username = "admin";
    private final String password = "admin";

    public List<Notification> getAll() {
        List<Notification> notifications = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM notification";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Notification notification = Notification.builder()
                        .id(resultSet.getLong("id"))
                        .bodyText(resultSet.getString("body_text"))
                        .date(resultSet.getTimestamp("date").toLocalDateTime())
                        .build();
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    public Notification getById(Long id) {
        Notification notification = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM notification WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                notification = Notification.builder()
                        .id(resultSet.getLong("id"))
                        .bodyText(resultSet.getString("body_text"))
                        .date(resultSet.getTimestamp("date").toLocalDateTime())
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notification;
    }

    public void save(Notification notification) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO notification (user_id, body_text, date) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, notification.getUserId());
            statement.setString(2, notification.getBodyText());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(notification.getDate()));
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                notification.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Notification notification) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "UPDATE notification SET user_id = ?, body_text = ?, date = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, notification.getUserId());
            statement.setString(2, notification.getBodyText());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(notification.getDate()));
            statement.setLong(4, notification.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "DELETE FROM notification WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Notification> getByUserId(Long userId) {
        List<Notification> notifications = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM notification WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Notification notification = Notification.builder()
                        .id(resultSet.getLong("id"))
                        .userId(resultSet.getLong("user_id"))
                        .bodyText(resultSet.getString("body_text"))
                        .date(resultSet.getTimestamp("date").toLocalDateTime())
                        .build();
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    public static void main(String[] args) {
        NotificationDAO notificationDAO = new NotificationDAO();
        Notification notification = Notification.builder()
                .id(1)
                .userId(2)
                .bodyText("Hellosnsn")
                .date(java.time.LocalDateTime.now())
                .build();
        // notificationDAO.save(notification);
        // System.out.println(notificationDAO.getAll());
        // notification.setBodyText("Hi");
        // notificationDAO.update(notification);
        // System.out.println(notificationDAO.getAll());
        notificationDAO.delete(2L);
        // System.out.println(notificationDAO.getAll());
    }

}
