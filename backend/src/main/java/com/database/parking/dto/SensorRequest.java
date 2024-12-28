package com.database.parking.dto;

import com.database.parking.enums.SpotStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SensorRequest {
    private SpotStatus status;
}