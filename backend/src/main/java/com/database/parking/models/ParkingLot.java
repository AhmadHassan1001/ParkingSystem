package com.database.parking.models;

import java.util.List;

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
public class ParkingLot {
    long id;
    String name;
    long locationId;
    long managerId;
    int capacity;
    double basicPrice;
}