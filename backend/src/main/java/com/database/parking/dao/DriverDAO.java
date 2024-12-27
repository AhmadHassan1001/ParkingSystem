package com.database.parking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.database.parking.enums.PaymentMethod;
import com.database.parking.enums.Role;
import com.database.parking.models.Driver;
import com.database.parking.models.User;

@Repository
public class DriverDAO {
    private static final String url = "jdbc:mysql://localhost:3306/parking_management_system";
    private static final String username = "admin";
    private static final String password = "admin";
    
    public List<Driver> getAll() {
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM driver";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Driver driver = Driver.builder()
                        .userId(result.getLong("id"))
                        .licensePlateNumber(result.getString("license_plate_number"))
                        .paymentMethod(PaymentMethod.valueOf(result.getString("payment_method").toUpperCase()))
                        .build();
                drivers.add(driver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drivers;
    }

    public Driver getById(Long userId) {
        Driver driver = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM driver WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, userId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                driver = Driver.builder()
                        .userId(result.getLong("user_id"))
                        .licensePlateNumber(result.getString("license_plate_number"))
                        .paymentMethod(PaymentMethod.valueOf(result.getString("payment_method").toUpperCase()))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }

    public void save(Driver driver) {
      try (Connection connection = DriverManager.getConnection(url, username, password)) {
        String sql = "INSERT INTO driver (user_id, license_plate_number, payment_method) VALUES (?, ?, ?)";
        System.out.println("Payment Method: ");
        System.out.println(driver.getPaymentMethod());
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, driver.getUserId());
        statement.setString(2, driver.getLicensePlateNumber());
        statement.setString(3, driver.getPaymentMethod().name());
        statement.executeUpdate();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    public void update(Driver driver) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "UPDATE driver SET license_plate_number = ?, payment_method = ? WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, driver.getLicensePlateNumber());
            statement.setString(2, driver.getPaymentMethod().name());
            statement.setLong(3, driver.getUserId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "DELETE FROM driver WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        DriverDAO driverDAO = new DriverDAO();

        // save 5 drivers
        for (int i = 1; i <= 5; i++) {
            User user = User.builder()
                    .name("Driver " + i)
                    .phone("123456789" + i)
                    .role(Role.DRIVER)
                    .password("password")
                    .build();
            userDAO.save(user);

            Driver driver = Driver.builder()
                    .userId(user.getId())
                    .licensePlateNumber("ABC" + i)
                    .paymentMethod(PaymentMethod.CASH)
                    .build();
            driverDAO.save(driver);
        }


        // update driver with id 1
        Driver driver = driverDAO.getById(1L);
        driver.setLicensePlateNumber("XYZ");
        driver.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        driverDAO.update(driver);

    
    }

}