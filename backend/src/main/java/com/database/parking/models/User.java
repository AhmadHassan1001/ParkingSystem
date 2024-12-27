package com.database.parking.models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.database.parking.enums.Role;

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


    public static String hashPassword(String password) {
      try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
          String hex = Integer.toHexString(0xff & b);
          if (hex.length() == 1) hexString.append('0');
          hexString.append(hex);
        }
        return hexString.toString();
      } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
      }
    }

    
    // override builder to use hashPassword()
    public static UserBuilder builder() {
        return new UserBuilder() {
            @Override
            public User build() {
                User user = super.build();
                user.setPassword(hashPassword(user.getPassword()));
                return user;
            }
        };
    }    

}