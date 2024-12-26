package com.database.parking.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Penalty {
    long id;
    long userId;
    long parkingLotId;
    String description;
    double amount;
}
