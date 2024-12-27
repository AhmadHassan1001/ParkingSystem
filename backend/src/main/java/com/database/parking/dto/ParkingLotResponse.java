package com.database.parking.dto;

import com.database.parking.models.Location;

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
}