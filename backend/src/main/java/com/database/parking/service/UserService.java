package com.database.parking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.database.parking.models.Driver;
import com.database.parking.models.Location;
import com.database.parking.models.ParkingLot;
import com.database.parking.models.User;
import com.database.parking.enums.Role;
import com.database.parking.dao.DriverDAO;
import com.database.parking.dao.LocationDAO;
import com.database.parking.dao.ParkingLotDAO;
import com.database.parking.dao.UserDAO;
import com.database.parking.dto.SignupRequestDriver;
import com.database.parking.dto.SignupRequestParkingLot;
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

    public TokenResponse signupParkingLotManager(SignupRequestParkingLot signupRequestParkingLot) {
        User user = User.builder()
                .name(signupRequestParkingLot.getName())
                .password(signupRequestParkingLot.getPassword())
                .phone(signupRequestParkingLot.getPhone())
                .role(Role.MANAGEMENT)
                .build();
        userDAO.save(user);

        Location location = Location.builder()
                .city(signupRequestParkingLot.getCity())
                .street(signupRequestParkingLot.getStreet())
                .mapLink(signupRequestParkingLot.getLocationLink())
                .build();
        locationDAO.save(location);

        ParkingLot parkingLot = ParkingLot.builder()
                .locationId(location.getId())
                .capacity(signupRequestParkingLot.getCapacity())
                .basicPrice(signupRequestParkingLot.getPrice())
                .managerId(user.getId())
                .build();
        parkingLotDAO.save(parkingLot);

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