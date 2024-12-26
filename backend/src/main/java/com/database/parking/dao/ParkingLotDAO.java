package com.database.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.database.parking.Entity.Location;
import com.database.parking.Entity.ParkingLot;

public class ParkingLotDAO {
    private final String url = "jdbc:mysql://localhost:3306/parking_management_system";
    private final String username = "admin";
    private final String password = "admin";

    public List<ParkingLot> getAll() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM parking_lot";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ParkingLot parkingLot = ParkingLot.builder()
                        .id(resultSet.getLong("id"))
                        .locationId(resultSet.getLong("location_id"))
                        .capacity(resultSet.getInt("capacity"))
                        .basicPrice(resultSet.getDouble("basic_price"))
                        .build();
                parkingLots.add(parkingLot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parkingLots;
    }

    public ParkingLot getById(long id) {
        ParkingLot parkingLot = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM parking_lot WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                parkingLot = ParkingLot.builder()
                        .id(resultSet.getLong("id"))
                        .locationId(resultSet.getLong("location_id"))
                        .capacity(resultSet.getInt("capacity"))
                        .basicPrice(resultSet.getDouble("basic_price"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parkingLot;
    }

    public void save(ParkingLot parkingLot) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO parking_lot (location_id, capacity, basic_price) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, parkingLot.getLocationId());
            statement.setInt(2, parkingLot.getCapacity());
            statement.setDouble(3, parkingLot.getBasicPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                parkingLot.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(ParkingLot parkingLot) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "UPDATE parking_lot SET location_id = ?, capacity = ?, basic_price = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, parkingLot.getLocationId());
            statement.setInt(2, parkingLot.getCapacity());
            statement.setDouble(3, parkingLot.getBasicPrice());
            statement.setLong(4, parkingLot.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "DELETE FROM parking_lot WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ParkingLotDAO parkingLotDAO = new ParkingLotDAO();
        LocationDAO locationDAO = new LocationDAO();

        Location location = Location.builder()
                .city("Istanbul")
                .street("Kadikoy")
                .mapLink("https://goo.gl/maps/1")
                .build();
        locationDAO.save(location);
        
        ParkingLot parkingLot = ParkingLot.builder()
                .locationId(location.getId())
                .capacity(100)
                .basicPrice(10.0)
                .build();
        parkingLotDAO.save(parkingLot);

        // ParkingLot parkingLot = parkingLotDAO.getById(4L);
        // parkingLot.setCapacity(200);
        // parkingLotDAO.update(parkingLot);

        // parkingLotDAO.getAll().forEach(System.out::println);

        // parkingLotDAO.delete(parkingLot.getId());

        
    
    }

}
