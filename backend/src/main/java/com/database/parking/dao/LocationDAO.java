package com.database.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.database.parking.models.Location;

@Repository
public class LocationDAO {
    private final String url = "jdbc:mysql://localhost:3306/parking_management_system";
    private final String username = "admin";
    private final String password = "admin";

    public List<Location> getAll() {
        List<Location> locations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM location";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Location location = Location.builder()
                        .id(resultSet.getLong("id"))
                        .city(resultSet.getString("city"))
                        .street(resultSet.getString("street"))
                        .mapLink(resultSet.getString("map_link"))
                        .build();
                locations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }


    public Location getById(long id) {
        Location location = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM location WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                location = Location.builder()
                        .id(resultSet.getLong("id"))
                        .city(resultSet.getString("city"))
                        .street(resultSet.getString("street"))
                        .mapLink(resultSet.getString("map_link"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return location;
    }

    public void save(Location location) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO location (city, street, map_link) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, location.getCity());
            statement.setString(2, location.getStreet());
            statement.setString(3, location.getMapLink());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                location.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Location location) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "UPDATE location SET city = ?, street = ?, map_link = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, location.getCity());
            statement.setString(2, location.getStreet());
            statement.setString(3, location.getMapLink());
            statement.setLong(4, location.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   

    public void delete(long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "DELETE FROM location WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        LocationDAO locationDAO = new LocationDAO();
        Location location = Location.builder()
                .city("Istanbul")
                .street("Kadikoy")
                .mapLink("https://www.google.com/maps/place/41.0082,28.9784")
                .build();
        locationDAO.save(location);
        System.out.println(locationDAO.getAll());
        location.setCity("Ankara");
        locationDAO.update(location);
        System.out.println(locationDAO.getAll());
        locationDAO.delete(1L);
        System.out.println(locationDAO.getAll());
    }
    
}
