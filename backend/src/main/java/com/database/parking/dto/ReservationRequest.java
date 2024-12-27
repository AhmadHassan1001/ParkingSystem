package com.database.parking.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}