package com.database.parking.enums;

public enum SpotType {
    REGULAR("Regular"),
    DISABLED("Disabled"),
    EV("EV Charging");

    private final String type;

    SpotType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
