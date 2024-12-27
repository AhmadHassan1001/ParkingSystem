package com.database.parking.enums;

public enum SpotStatus {
    OCCUPIED("Occupied"),
    AVAILABLE("Available"),
    RESERVED("Reserved");
    
    private final String status;

    SpotStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
