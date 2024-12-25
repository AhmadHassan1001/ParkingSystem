package com.database.parking.Entity;

import java.util.List;

import com.database.parking.Enums.SpotType;

public class ParkingLot{
    long id;

    Location location;
    int capacity;
    double price;

    List<ParkingSpot> parkingSpots;

    List<SpotType> spotTypes;
    
    Manager manager;
}
