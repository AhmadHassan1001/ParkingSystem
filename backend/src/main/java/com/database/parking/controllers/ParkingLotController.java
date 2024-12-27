package com.database.parking.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.database.parking.models.ParkingLot;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.beans.factory.annotation.Autowired;
import com.database.parking.dao.ParkingLotDAO;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotDAO ParkingLotDAO;
    
    @GetMapping
    public List<ParkingLot> getParkingLots() {
        return ParkingLotDAO.getAll();
    }


}
