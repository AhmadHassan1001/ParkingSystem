package com.database.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.database.parking.enums.SpotStatus;
import com.database.parking.enums.SpotType;
import com.database.parking.models.ParkingSpot;


@Repository
public class ParkingSpotDAO {
    private static final String url = "jdbc:mysql://localhost:3306/parking_management_system";
    private static final String username = "admin";
    private static final String password = "admin";

    public List<ParkingSpot> getAll() throws SQLException{
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM parking_spot";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String type = result.getString("type").toUpperCase();
                if ("EV CHARGING".equals(type))
                  type = "EV";
                ParkingSpot parkingSpot = ParkingSpot.builder()
                        .id(result.getLong("id"))
                        .parkingLotId(result.getLong("parking_lot_id"))
                        .type(SpotType.valueOf(type))
                        .status(SpotStatus.valueOf(result.getString("status").toUpperCase()))
                        .build();
                parkingSpots.add(parkingSpot);
            }
        } 
        return parkingSpots;
    }

    public ParkingSpot getById(Long id)throws SQLException {
        ParkingSpot parkingSpot = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM parking_spot WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String type = result.getString("type").toUpperCase();
                if ("EV CHARGING".equals(type))
                  type = "EV";
                parkingSpot = ParkingSpot.builder()
                        .id(result.getLong("id"))
                        .parkingLotId(result.getLong("parking_lot_id"))
                        .type(SpotType.valueOf(type))
                        .status(SpotStatus.valueOf(result.getString("status").toUpperCase()))
                        .build();
            }
        } 
        return parkingSpot;
    }

    public List<ParkingSpot> getByParkingLotId(Long parkingLotId) throws SQLException{
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM parking_spot WHERE parking_lot_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, parkingLotId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String type = result.getString("type").toUpperCase();
                if ("EV CHARGING".equals(type))
                  type = "EV";
                ParkingSpot parkingSpot = ParkingSpot.builder()
                        .id(result.getLong("id"))
                        .parkingLotId(result.getLong("parking_lot_id"))
                        .type(SpotType.valueOf(type))
                        .status(SpotStatus.valueOf(result.getString("status").toUpperCase()))
                        .build();
                parkingSpots.add(parkingSpot);
            }
        } 
        return parkingSpots;
    }

    public void save(ParkingSpot parkingSpot) throws SQLException{
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO parking_spot (parking_lot_id, type, status) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, parkingSpot.getParkingLotId());
            statement.setString(2, parkingSpot.getType().getType());
            statement.setString(3, parkingSpot.getStatus().getStatus());
            statement.executeUpdate();
            
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                parkingSpot.setId(result.getLong(1));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(ParkingSpot parkingSpot) throws SQLException{
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "UPDATE parking_spot SET parking_lot_id = ?, type = ?, status = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            String type = parkingSpot.getType().name();
            if(type.equals("EV"))
              type = "EV Charging";
            statement.setLong(1, parkingSpot.getParkingLotId());
            statement.setString(2, type);
            statement.setString(3, parkingSpot.getStatus().name());
            statement.setLong(4, parkingSpot.getId());
            statement.executeUpdate();
        } 
    }

    public void delete(Long id)throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "DELETE FROM parking_spot WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } 
    }

    // public static void main(String[] args) {
    //     ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
        
    //     List<ParkingSpot> parkingSpots = parkingSpotDAO.getAll();
    //     System.out.println(parkingSpots);

    //     ParkingSpot parkingSpot = parkingSpotDAO.getById(3L);
    //     System.out.println(parkingSpot);

    //     ParkingSpot newParkingSpot = ParkingSpot.builder()
    //             .parkingLotId(3L)
    //             .type(SpotType.REGULAR)
    //             .status(SpotStatus.AVAILABLE)
    //             .build();
    //     parkingSpotDAO.save(newParkingSpot);
    //     System.out.println(newParkingSpot);

    //     newParkingSpot.setStatus(SpotStatus.OCCUPIED);
    //     parkingSpotDAO.update(newParkingSpot);
    //     System.out.println(newParkingSpot);

    //     parkingSpotDAO.delete(3L);
    // }
}