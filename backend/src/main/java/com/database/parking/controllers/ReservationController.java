package com.database.parking.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.database.parking.dao.ParkingLotDAO;
import com.database.parking.dao.ParkingSpotDAO;
import com.database.parking.dao.ReservationDAO;
import com.database.parking.dto.ReservationInfo;
import com.database.parking.enums.ReservationStatus;
import com.database.parking.models.Reservation;

import ch.qos.logback.core.status.Status;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/reservation")
@CrossOrigin
public class ReservationController {
    
    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private ParkingSpotDAO parkingSpotDAO;

    @Autowired
    private ParkingLotDAO parkingLotDAO;


    @PostMapping("/{id}/calculate-cost")
    public double calculateCost (@PathVariable long parkingSpotId, LocalDateTime startTime, LocalDateTime endTime) {
        if (checkReservationDuration(parkingSpotId, startTime, endTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation duration is invalid");
        }
        long parkingLotId = parkingSpotDAO.getById(parkingSpotId).getParkingLotId();
        Double cost = parkingLotDAO.getDynamicprice(parkingLotId) * (endTime.getHour() - startTime.getHour());

        return cost;
    }
    
    @PostMapping("/{id}/reserve")
    public ResponseEntity<Reservation> reserve (@PathVariable long parkingSpotId, LocalDateTime startTime, LocalDateTime endTime) {
        if (checkReservationDuration(parkingSpotId, startTime, endTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation duration is invalid");
        }
        Reservation reservation = Reservation.builder()
        // to be changed when implementing authentication
            .userId(1L)
        //
            .parkingSpotId(parkingSpotId)
            .startTime(startTime)
            .endTime(endTime)
            .status(ReservationStatus.ACTIVE)
            .cost(calculateCost(parkingSpotId, startTime, endTime))
            .isPaid(false)
            .build();
        reservationDAO.save(reservation);

        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }


    boolean checkReservationDuration (long parkingSpotId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Reservation> reservations = reservationDAO.getByParkingSpotId(parkingSpotId);
        for (Reservation reservation : reservations) {
            if (reservation.getStatus().equals(ReservationStatus.ACTIVE)) {
                LocalDateTime reservationStartTime = reservation.getStartTime();
                LocalDateTime reservationEndTime = reservation.getEndTime();
                if (startTime.isBefore(reservationEndTime) && endTime.isAfter(reservationStartTime)) {
                    return false;
                }
            }
        }

        return true;
    }

}
