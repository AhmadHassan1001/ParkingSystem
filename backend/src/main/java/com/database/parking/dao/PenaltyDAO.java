package com.database.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.database.parking.models.Penalty;

public class PenaltyDAO {
    private static final String url = "jdbc:mysql://localhost:3306/parking_management_system";
    private static final String username = "admin";
    private static final String password = "admin";
    
    public List<Penalty> getAll() {
        List<Penalty> penalties = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM penalty";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Penalty penalty = Penalty.builder()
                        .id(result.getLong("id"))
                        .userId(result.getLong("user_id"))
                        .parkingLotId(result.getLong("parking_lot_id"))
                        .description(result.getString("description"))
                        .amount(result.getDouble("amount"))
                        .build();
                penalties.add(penalty);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return penalties;
    }

    public List<Penalty> getByUserId(Long userId) {
        List<Penalty> penalties = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM penalty WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Penalty penalty = Penalty.builder()
                        .id(result.getLong("id"))
                        .userId(result.getLong("user_id"))
                        .parkingLotId(result.getLong("parking_lot_id"))
                        .description(result.getString("description"))
                        .amount(result.getDouble("amount"))
                        .build();
                penalties.add(penalty);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return penalties;
    }

    public Penalty getById(Long id) {
        Penalty penalty = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM penalty WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                penalty = Penalty.builder()
                        .id(result.getLong("id"))
                        .userId(result.getLong("user_id"))
                        .parkingLotId(result.getLong("parking_lot_id"))
                        .description(result.getString("description"))
                        .amount(result.getDouble("amount"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return penalty;
    }

    public void save(Penalty penalty) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO penalty (user_id, parking_lot_id, description, amount) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, penalty.getUserId());
            statement.setLong(2, penalty.getParkingLotId());
            statement.setString(3, penalty.getDescription());
            statement.setDouble(4, penalty.getAmount());
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                penalty.setId(result.getLong(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    public void update(Penalty penalty) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "UPDATE penalty SET user_id = ?, parking_lot_id = ?, description = ?, amount = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, penalty.getUserId());
            statement.setLong(2, penalty.getParkingLotId());
            statement.setString(3, penalty.getDescription());
            statement.setDouble(4, penalty.getAmount());
            statement.setLong(5, penalty.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "DELETE FROM penalty WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PenaltyDAO penaltyDAO = new PenaltyDAO();
        
        // Penalty penalty1 = Penalty.builder()
        //         .userId(1L)
        //         .parkingLotId(3L)
        //         .description("Parking in a no parking zone")
        //         .amount(100)
        //         .build();
        // penaltyDAO.save(penalty1);

        // Penalty penalty2 = Penalty.builder()
        //         .userId(2L)
        //         .parkingLotId(5L)
        //         .description("Parking in a no parking zone")
        //         .amount(100)
        //         .build();
        // penaltyDAO.save(penalty2);

        // Penalty penalty3 = Penalty.builder()
        //         .userId(3L)
        //         .parkingLotId(3L)
        //         .description("Parking in a no parking zone")
        //         .amount(100)
        //         .build();
        // penaltyDAO.save(penalty3);


        // List<Penalty> penalties = penaltyDAO.getAll();
        // for (Penalty penalty : penalties) {
        //     System.out.println(penalty);
        // }

        // Penalty penalty = penaltyDAO.getById(3L);
        // penalty.setAmount(200);
        // penaltyDAO.update(penalty);

        penaltyDAO.delete(6L);

    }


}
