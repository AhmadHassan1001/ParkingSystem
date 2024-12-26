package com.database.parking.Service;

import com.database.parking.Entity.Driver;
import com.database.parking.Entity.User;
import com.database.parking.dao.DriverDAO;
import com.database.parking.dao.UserDAO;

import java.util.List;

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

    public void saveDriverUser(Driver driver,User user) {
        userDAO.save(user);
        driver.setUserId(user.getId());
        driverDAO.save(driver);
    }
}