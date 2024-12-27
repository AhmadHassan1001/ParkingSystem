package com.database.parking.dto;

import java.util.List;

import com.database.parking.enums.SpotStatus;
import com.database.parking.enums.SpotType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParkingSpotResponse {
    long id;
    long parkingLotId;
    SpotType type;
    SpotStatus status;
    List<ReservationInfo> reservationsInfo;
}
