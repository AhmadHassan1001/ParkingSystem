package com.database.parking.controllers;
import com.database.parking.dao.LocationDAO;
import com.database.parking.models.ParkingLot;
import com.database.parking.models.ParkingSpot;
import com.database.parking.models.User;
import com.database.parking.service.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.database.parking.dao.ParkingLotDAO;
import com.database.parking.dao.ParkingSpotDAO;
import com.database.parking.dto.ParkingLotResponse;
import com.database.parking.dto.SensorRequest;
import com.database.parking.enums.SpotStatus;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;



@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/iot")
public class IOTController {

  @Autowired
  ParkingSpotDAO parkingSpotDAO;
  @Autowired
  ParkingLotDAO parkingLotDAO;

  @Autowired
  private UserService userService;
  
  @PostMapping("/sensor/{spotId}")
  public void sensorUpdate(@RequestHeader("Authorization") String token, @PathVariable long spotId, @RequestBody SensorRequest status) {
    try {
      String bearerToken = token.substring(7); // Remove "Bearer " prefix
      User user = userService.getUserFromToken(bearerToken);
      ParkingSpot spot = parkingSpotDAO.getById(spotId);
      ParkingLot lot = parkingLotDAO.getById(spot.getParkingLotId());
      
      if(lot.getManagerId() != user.getId()) {
        return;
      }
  
      spot.setStatus(status.getStatus());
      parkingSpotDAO.update(spot);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parking spot does not exist");
    }
  }


  
}
