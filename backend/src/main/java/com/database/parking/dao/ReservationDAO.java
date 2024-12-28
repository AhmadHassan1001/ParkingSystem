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


    public void reserve (Reservation reservation) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(false); 
        
        try {
            String checkAvailabilityQuery = "SELECT * FROM parking_spot WHERE id = ? AND status = 'AVAILABLE' FOR UPDATE";
            try (PreparedStatement checkAvailabilityStmt = connection.prepareStatement(checkAvailabilityQuery)) {
                checkAvailabilityStmt.setLong(1, reservation.getParkingSpotId());
                ResultSet resultSet = checkAvailabilityStmt.executeQuery();

                if (!resultSet.next()) {
                    throw new SQLException("Parking spot is not available");
                }
            }

            String updateParkingSpotQuery = "UPDATE parking_spot SET status = 'RESERVED' WHERE id = ?";
            try (PreparedStatement updateParkingSpotStmt = connection.prepareStatement(updateParkingSpotQuery)) {
                updateParkingSpotStmt.setLong(1, reservation.getParkingSpotId());
                updateParkingSpotStmt.executeUpdate();
            }

            String insertReservationQuery = "INSERT INTO reservation (user_id, parking_spot_id, start_time, end_time, cost, status, is_paid) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertReservationStmt = connection.prepareStatement(insertReservationQuery)) {
                insertReservationStmt.setLong(1, reservation.getUserId());
                insertReservationStmt.setLong(2, reservation.getParkingSpotId());
                insertReservationStmt.setTimestamp(3, java.sql.Timestamp.valueOf(reservation.getStartTime()));
                insertReservationStmt.setTimestamp(4, java.sql.Timestamp.valueOf(reservation.getEndTime()));
                insertReservationStmt.setDouble(5, reservation.getCost());
                insertReservationStmt.setString(6, reservation.getStatus().name());
                insertReservationStmt.setBoolean(7, reservation.isPaid());
                insertReservationStmt.executeUpdate();
            }
            
            
            String insertNotificationQuery = "INSERT INTO notification (user_id, body_text, date) VALUES (?, ?, ?)";
            try (PreparedStatement insertNotificationStmt = connection.prepareStatement(insertNotificationQuery)) {
                insertNotificationStmt.setLong(1, reservation.getUserId());
                
                insertNotificationStmt.setString(2, "You have successfully reserved a parking spot with id = " + reservation.getParkingSpotId());
                insertNotificationStmt.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                insertNotificationStmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }   
    }
    

    public List<Reservation> getAll() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        Connection connection = DriverManager.getConnection(url, username, password);
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
        return reservations;
    }

    public Reservation getById(long id) throws SQLException {
        Reservation reservation = null;
        Connection connection = DriverManager.getConnection(url, username, password);
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
        return reservation;
    }

    public List<Reservation> getByUserId(long userId) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        Connection connection = DriverManager.getConnection(url, username, password);
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
        return reservations;
    }

    public List<Reservation> getByParkingSpotId(long parkingSpotId) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        Connection connection = DriverManager.getConnection(url, username, password);
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
        return reservations;
    }

    public void save(Reservation reservation) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
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
    }

    public void update(Reservation reservation) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
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
    }

    public void delete(long id) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        String query = "DELETE FROM reservation WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        statement.executeUpdate();
    }

    
    public static void main (String[] args) {
        // ReservationDAO reservationDAO = new ReservationDAO();

        // Reservation reservation1 = Reservation.builder()
        //         .userId(1)
        //         .parkingSpotId(1)
        //         .startTime(java.time.LocalDateTime.now())
        //         .endTime(java.time.LocalDateTime.now().plusHours(2))
        //         .cost(10.0)
        //         .status(ReservationStatus.ACTIVE)
        //         .isPaid(false)
        //         .build();

        // reservationDAO.save(reservation1);

        // // Reservation reservation2 = reservationDAO.getById(4);
        // // reservation2.setCost(20.0);
        // // reservationDAO.update(reservation2);

        // reservationDAO.delete(4);
        
        


        // List<Reservation> reservations = reservationDAO.getAll();
        // for (Reservation reservation : reservations) {
        //     System.out.println(reservation);
        // }
    }

}
