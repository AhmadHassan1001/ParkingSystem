package com.database.parking.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.database.parking.dao.LocationDAO;

import com.database.parking.models.ParkingLot;
import com.database.parking.models.ParkingSpot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.database.parking.dao.ParkingLotDAO;
import com.database.parking.dao.ParkingSpotDAO;
import com.database.parking.dto.ParkingLotResponse;
import com.database.parking.models.ParkingLot;
import com.database.parking.models.ParkingSpot;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotDAO parkingLotDAO;

    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private ParkingSpotDAO parkingSpotDAO;
    
    @GetMapping
    public List<ParkingLotResponse> getParkingLots() {
        try {
            List<ParkingLot> parkingLots = parkingLotDAO.getAll();
            List<ParkingSpot> parkingSpots = parkingSpotDAO.getAll();
            List<ParkingLotResponse> parkingLotResponses = parkingLots.stream().map(parkingLot -> {
                try {
                    return ParkingLotResponse.builder()
                        .id(parkingLot.getId())
                        .name(parkingLot.getName())
                        .location(locationDAO.getById(parkingLot.getLocationId()))
                        .managerId(parkingLot.getManagerId())
                        .capacity(parkingLot.getCapacity())
                        .basicPrice(parkingLot.getBasicPrice())
                        .parkingSpots(parkingSpots.stream().filter(parkingSpot -> parkingSpot.getParkingLotId() == parkingLot.getId()).toList())
                        .build();
                } catch (SQLException e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching location", e);
                }
            }).toList();

            return parkingLotResponses;
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching parking lots", e);
        }
    }

    @GetMapping("/{id}")

    public ParkingLotResponse getParkingLotById (@PathVariable("id") long id) throws SQLException  {
        ParkingLot parkingLot = parkingLotDAO.getById(id);
        List<ParkingSpot> parkingSpots = parkingSpotDAO.getByParkingLotId(id);
        ParkingLotResponse parkingLotResponse = ParkingLotResponse.builder()
            .id(parkingLot.getId())
            .name(parkingLot.getName())
            .location(locationDAO.getById(parkingLot.getLocationId()))
            .managerId(parkingLot.getManagerId())
            .capacity(parkingLot.getCapacity())
            .basicPrice(parkingLot.getBasicPrice())
            .parkingSpots(parkingSpots)
            .build();

        return parkingLotResponse;
    }


}
