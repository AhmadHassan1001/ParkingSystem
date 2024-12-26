package com.database.parking.Entity;

import com.database.parking.Enums.SpotStatus;
import com.database.parking.Enums.SpotType;

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
public class ParkingSpot {
    long id;
    long parkingLotId;
    SpotType type;
    SpotStatus status;
}
