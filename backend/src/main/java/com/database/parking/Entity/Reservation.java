package com.database.parking.Entity;

import com.database.parking.Enums.ReservationStatus;

public class Reservation {
    long reservationId;

    long driverId;
    long parkingLotId;

    long spotId;

    long startTime;
    long endTime;

    long penaltyAmount;
    long totalAmount;

    ReservationStatus status;
}
