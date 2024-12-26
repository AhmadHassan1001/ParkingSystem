package com.database.parking.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.database.parking.Entity.Driver;
import com.database.parking.Entity.Location;
import com.database.parking.Entity.ParkingLot;
import com.database.parking.Entity.User;
import com.database.parking.Enums.Role;
import com.database.parking.dao.DriverDAO;
import com.database.parking.dao.LocationDAO;
import com.database.parking.dao.ParkingLotDAO;
import com.database.parking.dao.UserDAO;
import com.database.parking.dto.LoginRequest;
import com.database.parking.dto.SignupRequestDriver;
import com.database.parking.dto.SignupRequestParkingLot;

@Service
public class UserService {

    private UserDAO userDAO = new UserDAO();
    private DriverDAO driverDAO = new DriverDAO();
    private LocationDAO locationDAO = new LocationDAO();
    private ParkingLotDAO parkingLotDAO = new ParkingLotDAO();

    public void save(User user) {
        userDAO.save(user);
    }

    public String login(LoginRequest loginRequest) {
        User user = userDAO.getByNameAndPassword(loginRequest.getName(), loginRequest.getPassword());
        if (user != null) {
            return String.valueOf(user.getId());
        }
        return null;
    }

    public String signupDriver(SignupRequestDriver signupRequestDriver) {
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
        return String.valueOf(user.getId());
    }

    public String signupParkingLotManager(SignupRequestParkingLot signupRequestParkingLot) {
        User user = User.builder()
                .name(signupRequestParkingLot.getName())
                .password(signupRequestParkingLot.getPassword())
                .phone(signupRequestParkingLot.getPhone())
                .role(Role.LOT_MANAGER)
                .build();
        userDAO.save(user);

        Location location = Location.builder()
                .city(signupRequestParkingLot.getCity())
                .street(signupRequestParkingLot.getStreet())
                .mapLink(signupRequestParkingLot.getLocationLink())
                .build();
        locationDAO.save(location);

        int capacity = signupRequestParkingLot.getDisabledSlots() +
                signupRequestParkingLot.getEvSlots() +
                signupRequestParkingLot.getRegularSlots();

        ParkingLot parkingLot = ParkingLot.builder()
                .locationId(location.getId())
                .capacity(capacity)
                .basicPrice(signupRequestParkingLot.getPrice())
                .managerId(user.getId())
                .parkingLotName(signupRequestParkingLot.getParkingLotName())
                .build();
        parkingLotDAO.save(parkingLot);

        return String.valueOf(user.getId());
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