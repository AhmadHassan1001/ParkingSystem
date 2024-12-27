// filepath: backend/src/main/java/com/database/parking/dto/SignupRequestParkingLot.java
package com.database.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignupRequestParkingLot {
    private String name;
    private String password;
    private String phone;
    private String city;
    private String street;
    private String locationLink;
    private int capacity;
    private double price;
    private int regularSlots;
    private int disabledSlots;
    private int evSlots;
}