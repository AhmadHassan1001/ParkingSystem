package com.database.parking.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.database.parking.models.Driver;
import com.database.parking.models.Location;
import com.database.parking.models.ParkingLot;
import com.database.parking.models.ParkingSpot;
import com.database.parking.models.User;
import com.database.parking.enums.Role;
import com.database.parking.enums.SpotStatus;
import com.database.parking.enums.SpotType;
import com.database.parking.dao.DriverDAO;
import com.database.parking.dao.LocationDAO;
import com.database.parking.dao.ParkingLotDAO;
import com.database.parking.dao.ParkingSpotDAO;
import com.database.parking.dao.UserDAO;
import com.database.parking.dto.SignupRequestDriver;
import com.database.parking.dto.SignupRequestParkingLot;
import com.database.parking.dto.TokenResponse;

@Service
public class UserService {

    private static final String SECRET_KEY = "lbfidughisybndirlg";

    private UserDAO userDAO = new UserDAO();
    private DriverDAO driverDAO = new DriverDAO();
    private LocationDAO locationDAO = new LocationDAO();
    private ParkingLotDAO parkingLotDAO = new ParkingLotDAO();
    private ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();

    public void save(User user) throws SQLException {
        userDAO.save(user);
    }

    public TokenResponse login(String name, String password) throws SQLException {
        User user = userDAO.getByNameAndPassword(name, password);
        if (user != null) {
            String jwt = Jwts.builder()
                    .setSubject(String.valueOf(user.getId()))
                    .claim("role", user.getRole().toString())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();
            return new TokenResponse(jwt);
        }
        return null;
    }
    
    public User getUserFromToken(String token) {
      try {
        String userId = Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
        return userDAO.getById(Long.parseLong(userId));
      } catch (Exception e) {
        return null;
      }
    }

    public User signupDriver(SignupRequestDriver signupRequestDriver) throws SQLException  {
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
        return user;
    }

    public User signupParkingLotManager(SignupRequestParkingLot signupRequestParkingLot) throws SQLException  {
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

        ParkingLot parkingLot = ParkingLot.builder()
                .name(signupRequestParkingLot.getParkingLotName())
                .locationId(location.getId())
                .capacity(signupRequestParkingLot.getCapacity())
                .basicPrice(signupRequestParkingLot.getPrice())
                .managerId(user.getId())
                .build();
        parkingLotDAO.save(parkingLot);

        // Parking Spots Creation
        if (signupRequestParkingLot.getCapacity() != signupRequestParkingLot.getDisabledSlots() 
                                                      + signupRequestParkingLot.getRegularSlots()
                                                      + signupRequestParkingLot.getEvSlots()) {
          throw new RuntimeException("Capacity should be equal to the sum of disabled, regular and EV slots");
        }

        // Regular parking spots
        for (int i = 1; i <= signupRequestParkingLot.getRegularSlots(); i++) {
          ParkingSpot parkingSpot = ParkingSpot.builder()
                  .parkingLotId(parkingLot.getId())
                  .type(SpotType.REGULAR)
                  .status(SpotStatus.AVAILABLE)
                  .build();
          parkingSpotDAO.save(parkingSpot);
        }

        // Disabled parking spots
        for (int i = 1; i <= signupRequestParkingLot.getDisabledSlots(); i++) {
          ParkingSpot parkingSpot = ParkingSpot.builder()
                  .parkingLotId(parkingLot.getId())
                  .type(SpotType.DISABLED)
                  .status(SpotStatus.AVAILABLE)
                  .build();
          parkingSpotDAO.save(parkingSpot);
        }

        // EV parking spots
        for (int i = 1; i <= signupRequestParkingLot.getEvSlots(); i++) {
          ParkingSpot parkingSpot = ParkingSpot.builder()
                  .parkingLotId(parkingLot.getId())
                  .type(SpotType.EV)
                  .status(SpotStatus.AVAILABLE)
                  .build();
          parkingSpotDAO.save(parkingSpot);
        }

        // (for simplicity, using id as token)
        return user;
    }

    public void update(User user) throws SQLException {
        userDAO.update(user);
    }

    public void delete(long id) throws SQLException {
        userDAO.delete(id);
    }

    public User getById(long id) throws SQLException {
        return userDAO.getById(id);
    }

    public List<User> getAll() throws SQLException {
        return userDAO.getAll();
    }

}