package com.database.parking.Entity;

import com.database.parking.Enums.PaymentMethod;

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
public class Driver {
    Long userId;
    String licensePlateNumber;
    PaymentMethod paymentMethod;
}
