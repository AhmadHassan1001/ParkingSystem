package com.database.parking.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.database.parking.dao.ParkingLotDAO;
import com.database.parking.dao.ParkingSpotDAO;
import com.database.parking.dao.ReservationDAO;
import com.database.parking.dto.ReservationRequest;
import com.database.parking.enums.ReservationStatus;
import com.database.parking.models.ParkingLot;
import com.database.parking.models.ParkingSpot;
import com.database.parking.models.Reservation;
import com.database.parking.models.User;
import com.database.parking.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;



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

    @Autowired
    private UserService userService;

    @PostMapping("/{parkingSpotId}/calculate-cost")
    public double calculateCost (@PathVariable long parkingSpotId, @RequestBody ReservationRequest reservationRequest) {
        LocalDateTime startTime = reservationRequest.getStartTime();
        LocalDateTime endTime = reservationRequest.getEndTime();
        if (!checkReservationDuration(parkingSpotId, startTime, endTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation duration is invalid");
        }
        long parkingLotId = parkingSpotDAO.getById(parkingSpotId).getParkingLotId();
        Double cost = parkingLotDAO.getDynamicprice(parkingLotId) * (endTime.getHour() - startTime.getHour());

        return cost;
    }
    
    @PostMapping("/{parkingSpotId}/reserve")
    public ResponseEntity<Reservation> reserve (@RequestHeader("Authorization") String token, @PathVariable long parkingSpotId, @RequestBody ReservationRequest reservationRequest) {
        String bearerToken = token.substring(7); // Remove "Bearer " prefix
        User user = userService.getUserFromToken(bearerToken);
        LocalDateTime startTime = reservationRequest.getStartTime();
        LocalDateTime endTime = reservationRequest.getEndTime();
        if (!checkReservationDuration(parkingSpotId, startTime, endTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation duration is invalid");
        }
        Reservation reservation = Reservation.builder()
            .userId(user.getId())
            .parkingSpotId(parkingSpotId)
            .startTime(startTime)
            .endTime(endTime)
            .status(ReservationStatus.ACTIVE)
            .cost(calculateCost(parkingSpotId, reservationRequest))
            .isPaid(false)
            .build();
        try {
            reservationDAO.save(reservation);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation could not be saved");
        }

        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }


    @PostMapping("/{reservationId}/pay")
    public ResponseEntity<Reservation> pay (@RequestHeader("Authorization") String token, @PathVariable long reservationId) {
        String bearerToken = token.substring(7); // Remove "Bearer " prefix
        User user = userService.getUserFromToken(bearerToken);
        try{
            Reservation reservation = reservationDAO.getById(reservationId);
            ParkingSpot parkingSpot = parkingSpotDAO.getById(reservation.getParkingSpotId());
            ParkingLot parkingLot = parkingLotDAO.getById(parkingSpot.getParkingLotId());
            if (parkingLot.getManagerId() != user.getId()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized to pay for this reservation");
            }
            reservation.setPaid(true);
            reservationDAO.update(reservation);
            return new ResponseEntity<>(reservation, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation could not be paid");
        }
    }


    boolean checkReservationDuration (long parkingSpotId, LocalDateTime startTime, LocalDateTime endTime) {
        try {
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
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

}
