package com.database.parking.Entity;

import java.time.LocalDateTime;

import com.database.parking.Enums.ReservationStatus;

public class Reservation {
    long id;

    long driverId;
    long spotId;

    LocalDateTime startTime;
    LocalDateTime endTime;

    double cost;
    ReservationStatus status;
}
