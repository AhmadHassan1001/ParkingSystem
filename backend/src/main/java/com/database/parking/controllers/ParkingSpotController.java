package com.database.parking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.database.parking.dao.ParkingSpotDAO;
import com.database.parking.dao.ReservationDAO;
import com.database.parking.dto.ParkingSpotResponse;
import com.database.parking.dto.ReservationInfo;
import com.database.parking.enums.ReservationStatus;
import com.database.parking.models.ParkingSpot;
import com.database.parking.models.Reservation;


@RestController
@RequestMapping("/parking-spots")
@CrossOrigin(origins = "http://localhost:3000")
public class ParkingSpotController {
    
    @Autowired
    private ParkingSpotDAO parkingSpotDAO;

    @Autowired
    private ReservationDAO reservationDAO;

    @GetMapping("/{id}")
    public ParkingSpotResponse getParkingSpotDetails (@PathVariable("id") long id) {
        try {
            ParkingSpot parkingSpot = parkingSpotDAO.getById(id);
            List<Reservation> reservations = reservationDAO.getByParkingSpotId(id);

            // choose reservations info whose status is ACTIVE
            List<ReservationInfo> reservationsInfo = reservations.stream()
            .filter(reservation -> reservation.getStatus().equals(ReservationStatus.COMPLETED)).map(reservation -> ReservationInfo.builder()
                .id(reservation.getId())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .isPaid(reservation.isPaid())
                .build()).toList();

            ParkingSpotResponse parkingSpotResponse = ParkingSpotResponse.builder()
                .id(parkingSpot.getId())
                .parkingLotId(parkingSpot.getParkingLotId())
                .type(parkingSpot.getType())
                .status(parkingSpot.getStatus())
                .reservationsInfo(reservationsInfo)
                .build();

            return parkingSpotResponse;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

}
