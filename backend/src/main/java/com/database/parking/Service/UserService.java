package com.database.parking.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.database.parking.Entity.Driver;
import com.database.parking.Entity.User;
import com.database.parking.Enums.Role;
import com.database.parking.dao.DriverDAO;
import com.database.parking.dao.LocationDAO;
import com.database.parking.dao.ParkingLotDAO;
import com.database.parking.dao.UserDAO;
import com.database.parking.dto.SignupRequestDriver;
import com.database.parking.dto.TokenResponse;

@Service
public class UserService {

    private UserDAO userDAO = new UserDAO();

    private DriverDAO driverDAO = new DriverDAO();

    private LocationDAO locationDAO = new LocationDAO();

    private ParkingLotDAO parkingLotDAO = new ParkingLotDAO();

    public void save(User user) {
        userDAO.save(user);
    }

    public TokenResponse login(String name, String password) {
        User user = userDAO.getByNameAndPassword(name, password);
        if (user != null) {
            // (for simplicity, using id as token)
            return new TokenResponse(String.valueOf(user.getId()), user.getRole().toString());
        }
        return null;
    }

    public TokenResponse signupDriver(SignupRequestDriver signupRequestDriver) {
        User user = User.builder()
                .name(signupRequestDriver.getName())
                .password(signupRequestDriver.getPassword())
                .phone(signupRequestDriver.getPhone())
                .role(Role.DRIVER)
                .build();
        userDAO.save(user);

        Driver driver = Driver.builder()
                .userId(user.getId())
                .licensePlateNumber(signupRequestDriver.getLicensePlateNumber())
                .paymentMethod(signupRequestDriver.getPaymentMethod())
                .build();
        driverDAO.save(driver);
        
        // (for simplicity, using id as token)
        return new TokenResponse(String.valueOf(user.getId()), user.getRole().toString());
    }

    public void update(User user) {
        userDAO.update(user);
    }

    public void delete(long id) {
        userDAO.delete(id);
    }

    public User getById(long id) {
        return userDAO.getById(id);
    }

    public List<User> getAll() {
        return userDAO.getAll();
    }
}