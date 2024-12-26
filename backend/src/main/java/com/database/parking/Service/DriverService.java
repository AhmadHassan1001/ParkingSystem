package com.database.parking.Service;

import com.database.parking.DTO.DriverCreateRequest;
import com.database.parking.Entity.Driver;
import com.database.parking.Entity.User;
import com.database.parking.dao.DriverDAO;
import com.database.parking.dao.UserDAO;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DriverService {
    private final DriverDAO driverDAO;
    private final UserDAO userDAO;
    public DriverService() {
        this.driverDAO = new DriverDAO();
        this.userDAO = new UserDAO();
    }

    public List<Driver> getAllDrivers() {
        return driverDAO.getAll();
    }

    public Driver getDriverById(Long userId) {
        return driverDAO.getById(userId);
    }

    public void saveDriver(Driver driver) {
        driverDAO.save(driver);
    }

    public void updateDriver(Driver driver) {
        driverDAO.update(driver);
    }

    public void deleteDriver(Long userId) {
        driverDAO.delete(userId);
    }

    public long createNewUserDriver(DriverCreateRequest request) {
        User user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .role(request.getRole())
                .password(request.getPassword())
                .build();

        userDAO.save(user);

        Driver driver = Driver.builder()
                .userId(user.getId())
                .licensePlateNumber(request.getLicensePlateNumber())
                .build();

        driverDAO.save(driver);

        return user.getId();
    }
}