package com.database.parking.Entity;

import java.util.List;

import com.database.parking.Enums.Role;

public class User{
    long id;
    String username;
    String password;
    String phone;
    Role role;
    List<Notification> notifications;
}