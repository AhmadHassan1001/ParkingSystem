package com.database.parking.Entity;

import com.database.parking.Enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User{
    long id;
    String name;
    String phone;
    Role role;
    String password;
}