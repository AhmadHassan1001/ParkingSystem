package com.database.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.database.parking.enums.ReservationStatus;
import com.database.parking.models.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDAO {
    private final String url = "jdbc:mysql://localhost:3306/parking_management_system";
    private final String username = "admin";
    private final String password = "admin";

    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM reservation";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Reservation reservation = Reservation.builder()
                        .id(resultSet.getLong("id"))
                        .userId(resultSet.getLong("user_id"))
                        .parkingSpotId(resultSet.getLong("parking_spot_id"))
                        .startTime(resultSet.getTimestamp("start_time").toLocalDateTime())
                        .endTime(resultSet.getTimestamp("end_time").toLocalDateTime())
                        .cost(resultSet.getDouble("cost"))
                        .status(ReservationStatus.valueOf(resultSet.getString("status").toUpperCase()))
                        .isPaid(resultSet.getBoolean("is_paid"))
                        .build();
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public Reservation getById(long id) {
        Reservation reservation = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM reservation WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                reservation = Reservation.builder()
                        .id(resultSet.getLong("id"))
                        .userId(resultSet.getLong("user_id"))
                        .parkingSpotId(resultSet.getLong("parking_spot_id"))
                        .startTime(resultSet.getTimestamp("start_time").toLocalDateTime())
                        .endTime(resultSet.getTimestamp("end_time").toLocalDateTime())
                        .cost(resultSet.getDouble("cost"))
                        .status(ReservationStatus.valueOf(resultSet.getString("status").toUpperCase()))
                        .isPaid(resultSet.getBoolean("is_paid"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    public List<Reservation> getByUserId(long userId) {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM reservation WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Reservation reservation = Reservation.builder()
                        .id(resultSet.getLong("id"))
                        .userId(resultSet.getLong("user_id"))
                        .parkingSpotId(resultSet.getLong("parking_spot_id"))
                        .startTime(resultSet.getTimestamp("start_time").toLocalDateTime())
                        .endTime(resultSet.getTimestamp("end_time").toLocalDateTime())
                        .cost(resultSet.getDouble("cost"))
                        .status(ReservationStatus.valueOf(resultSet.getString("status").toUpperCase()))
                        .isPaid(resultSet.getBoolean("is_paid"))
                        .build();
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public List<Reservation> getByParkingSpotId(long parkingSpotId) {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM reservation WHERE parking_spot_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, parkingSpotId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Reservation reservation = Reservation.builder()
                        .id(resultSet.getLong("id"))
                        .userId(resultSet.getLong("user_id"))
                        .parkingSpotId(resultSet.getLong("parking_spot_id"))
                        .startTime(resultSet.getTimestamp("start_time").toLocalDateTime())
                        .endTime(resultSet.getTimestamp("end_time").toLocalDateTime())
                        .cost(resultSet.getDouble("cost"))
                        .status(ReservationStatus.valueOf(resultSet.getString("status").toUpperCase()))
                        .isPaid(resultSet.getBoolean("is_paid"))
                        .build();
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public void save(Reservation reservation) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO reservation (user_id, parking_spot_id, start_time, end_time, cost, status, is_paid) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, reservation.getUserId());
            statement.setLong(2, reservation.getParkingSpotId());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(reservation.getStartTime()));
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(reservation.getEndTime()));
            statement.setDouble(5, reservation.getCost());
            statement.setString(6, reservation.getStatus().toString());
            statement.setBoolean(7, reservation.isPaid());
            statement.executeUpdate();
            
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                reservation.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Reservation reservation) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "UPDATE reservation SET user_id = ?, parking_spot_id = ?, start_time = ?, end_time = ?, cost = ?, status = ?, is_paid = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, reservation.getUserId());
            statement.setLong(2, reservation.getParkingSpotId());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(reservation.getStartTime()));
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(reservation.getEndTime()));
            statement.setDouble(5, reservation.getCost());
            statement.setString(6, reservation.getStatus().toString());
            statement.setBoolean(7, reservation.isPaid());
            statement.setLong(8, reservation.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "DELETE FROM reservation WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public static void main (String[] args) {
        ReservationDAO reservationDAO = new ReservationDAO();

        Reservation reservation1 = Reservation.builder()
                .userId(1)
                .parkingSpotId(1)
                .startTime(java.time.LocalDateTime.now())
                .endTime(java.time.LocalDateTime.now().plusHours(2))
                .cost(10.0)
                .status(ReservationStatus.ACTIVE)
                .isPaid(false)
                .build();

        reservationDAO.save(reservation1);

        // Reservation reservation2 = reservationDAO.getById(4);
        // reservation2.setCost(20.0);
        // reservationDAO.update(reservation2);

        reservationDAO.delete(4);
        
        


        List<Reservation> reservations = reservationDAO.getAll();
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

}
