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

import com.database.parking.models.ParkingLot;

@Repository
public class ParkingLotDAO {
    private final String url = "jdbc:mysql://localhost:3306/parking_management_system";
    private final String username = "admin";
    private final String password = "admin";


    public Double getDynamicprice (long id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT calculate_dynamic_price(?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            } else {
                return null;
            }
            
        }
    }

    public List<ParkingLot> getAll() throws SQLException {
        List<ParkingLot> parkingLots = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM parking_lot";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ParkingLot parkingLot = ParkingLot.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .managerId(resultSet.getLong("manager_id"))
                        .locationId(resultSet.getLong("location_id"))
                        .capacity(resultSet.getInt("capacity"))
                        .basicPrice(resultSet.getDouble("basic_price"))
                        .managerId(resultSet.getLong("manager_id"))
                        .build();
                parkingLots.add(parkingLot);
            }
        } 
        return parkingLots;
    }

    public ParkingLot getById(long id) throws SQLException  {
        ParkingLot parkingLot = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM parking_lot WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                parkingLot = ParkingLot.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .managerId(resultSet.getLong("manager_id"))
                        .locationId(resultSet.getLong("location_id"))
                        .capacity(resultSet.getInt("capacity"))
                        .basicPrice(resultSet.getDouble("basic_price"))
                        .managerId(resultSet.getLong("manager_id"))
                        .build();
            }
        } 
        return parkingLot;
    }

    public void save(ParkingLot parkingLot) throws SQLException  {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO parking_lot (name, manager_id, location_id, capacity, basic_price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, parkingLot.getName());
            statement.setLong(2, parkingLot.getManagerId());
            statement.setLong(3, parkingLot.getLocationId());
            statement.setInt(4, parkingLot.getCapacity());
            statement.setDouble(5, parkingLot.getBasicPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                parkingLot.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(ParkingLot parkingLot) throws SQLException  {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "UPDATE parking_lot SET name = ?, manager_id = ?, location_id = ?, capacity = ?, basic_price = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, parkingLot.getName());
            statement.setLong(2, parkingLot.getManagerId());
            statement.setLong(3, parkingLot.getLocationId());
            statement.setInt(4, parkingLot.getCapacity());
            statement.setDouble(5, parkingLot.getBasicPrice());
            statement.setLong(6, parkingLot.getId());
            statement.executeUpdate();
        } 
    }

    public void delete(long id) throws SQLException  {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "DELETE FROM parking_lot WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } 
    }



    public static void main(String[] args) throws SQLException  {
        ParkingLotDAO parkingLotDAO = new ParkingLotDAO();
        LocationDAO locationDAO = new LocationDAO();

        // Location location = Location.builder()
        //         .city("Istanbul")
        //         .street("Kadikoy")
        //         .mapLink("https://goo.gl/maps/1")
        //         .build();
        // locationDAO.save(location);
        
        // ParkingLot parkingLot = ParkingLot.builder()
        //         .name("Central Parking")
        //         .managerId(1L) // Assuming managerId is 1 for this example
        //         .locationId(2L)
        //         .capacity(100)
        //         .basicPrice(10.0)
        //         .build();
        // parkingLotDAO.save(parkingLot);

        // Update parking lot with id 1
        ParkingLot updatedParkingLot = parkingLotDAO.getById(2L);
        if (updatedParkingLot != null) {
            updatedParkingLot.setCapacity(200);
            updatedParkingLot.setName("dsmdm Parking");
            updatedParkingLot.setManagerId(2L); // Assuming new managerId is 2
            parkingLotDAO.update(updatedParkingLot);
        }

        // Print all parking lots
        // parkingLotDAO.getAll().forEach(System.out::println);

        // Delete parking lot with id 1
        // parkingLotDAO.delete(1L);
    }

}
