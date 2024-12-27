package com.database.parking.dto;

import com.database.parking.enums.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignupRequestDriver {
    private String name;
    private String password;
    private String phone;
    private String licensePlateNumber;
    private PaymentMethod paymentMethod;
}