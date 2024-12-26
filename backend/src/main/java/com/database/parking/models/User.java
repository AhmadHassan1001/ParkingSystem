package com.database.parking.models;

import com.database.parking.orm.Model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class User extends Model {
    public Long id;
    public String username;
    public String password;
    public String email;

    public User() {
        super();
    }
    
  
}
