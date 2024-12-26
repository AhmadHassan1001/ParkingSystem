package com.database.parking.Entity;

import java.time.LocalDateTime;

import com.database.parking.Enums.ReservationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    long id;

    long userId;
    long parkingSpotId;

    LocalDateTime startTime;
    LocalDateTime endTime;

    double cost;
    ReservationStatus status;

    boolean isPaid;
}
