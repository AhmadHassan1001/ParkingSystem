package com.database.parking.Entity;

import com.database.parking.Enums.SpotStatus;
import com.database.parking.Enums.SpotType;

public class ParkingSpot {
    long id;
    long parkingLotId;
    SpotType type;
    SpotStatus status;
}
