package com.database.parking.dto;

import java.util.List;

import com.database.parking.models.Location;
import com.database.parking.models.ParkingSpot;

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
public class ParkingLotResponse {
    long id;
    String name;
    Location location;
    long managerId;
    int capacity;
    double basicPrice;
    List<ParkingSpot> parkingSpots;
}