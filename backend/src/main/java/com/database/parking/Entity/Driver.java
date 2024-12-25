package com.database.parking.Entity;

import java.util.List;

public class Driver{
    String licensePlateNumber;
    String paymentMethod;
    List<ParkingSpot> reservations;
    List<Penalty> penalties;
}
