package com.database.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public List<ParkingSpot> getAll() {
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM parking_spot";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                ParkingSpot parkingSpot = ParkingSpot.builder()
                        .id(result.getLong("id"))
                        .parkingLotId(result.getLong("parking_lot_id"))
                        .type(SpotType.valueOf(result.getString("type").toUpperCase()))
                        .status(SpotStatus.valueOf(result.getString("status").toUpperCase()))
                        .build();
                parkingSpots.add(parkingSpot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parkingSpots;
    }

    public ParkingSpot getById(Long id) {
        ParkingSpot parkingSpot = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM parking_spot WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                parkingSpot = ParkingSpot.builder()
                        .id(result.getLong("id"))
                        .parkingLotId(result.getLong("parking_lot_id"))
                        .type(SpotType.valueOf(result.getString("type").toUpperCase()))
                        .status(SpotStatus.valueOf(result.getString("status").toUpperCase()))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parkingSpot;
    }

    public List<ParkingSpot> getByParkingLotId(Long parkingLotId) {
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM parking_spot WHERE parking_lot_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, parkingLotId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                ParkingSpot parkingSpot = ParkingSpot.builder()
                        .id(result.getLong("id"))
                        .parkingLotId(result.getLong("parking_lot_id"))
                        .type(SpotType.valueOf(result.getString("type").toUpperCase()))
                        .status(SpotStatus.valueOf(result.getString("status").toUpperCase()))
                        .build();
                parkingSpots.add(parkingSpot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parkingSpots;
    }

    public void save(ParkingSpot parkingSpot) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO parking_spot (parking_lot_id, type, status) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, parkingSpot.getParkingLotId());
            statement.setString(2, parkingSpot.getType().name());
            statement.setString(3, parkingSpot.getStatus().name());
            statement.executeUpdate();
            
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                parkingSpot.setId(result.getLong(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(ParkingSpot parkingSpot) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "UPDATE parking_spot SET parking_lot_id = ?, type = ?, status = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, parkingSpot.getParkingLotId());
            statement.setString(2, parkingSpot.getType().name());
            statement.setString(3, parkingSpot.getStatus().name());
            statement.setLong(4, parkingSpot.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "DELETE FROM parking_spot WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
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