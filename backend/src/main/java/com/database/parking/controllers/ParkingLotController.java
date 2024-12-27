package com.database.parking.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.database.parking.dao.LocationDAO;
import com.database.parking.models.ParkingLot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.database.parking.dao.ParkingLotDAO;
import com.database.parking.dto.ParkingLotResponse;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotDAO ParkingLotDAO;

    @Autowired
    private LocationDAO LocationDAO;
    
    @GetMapping
    public List<ParkingLotResponse> getParkingLots() {
        List<ParkingLot> parkingLots = ParkingLotDAO.getAll();
        List<ParkingLotResponse> parkingLotResponses = parkingLots.stream().map(parkingLot -> ParkingLotResponse.builder()
            .id(parkingLot.getId())
            .name(parkingLot.getName())
            .location(LocationDAO.getById(parkingLot.getLocationId()))
            .managerId(parkingLot.getManagerId())
            .capacity(parkingLot.getCapacity())
            .basicPrice(parkingLot.getBasicPrice())
            .build()).toList();

        return parkingLotResponses;
    }

    @GetMapping("/{id}")
    public ParkingLotResponse getParkingLotById (@PathVariable long id) {
        ParkingLot parkingLot = ParkingLotDAO.getById(id);
        ParkingLotResponse parkingLotResponse = ParkingLotResponse.builder()
            .id(parkingLot.getId())
            .name(parkingLot.getName())
            .location(LocationDAO.getById(parkingLot.getLocationId()))
            .managerId(parkingLot.getManagerId())
            .capacity(parkingLot.getCapacity())
            .basicPrice(parkingLot.getBasicPrice())
            .build();

        return parkingLotResponse;
    }


}
