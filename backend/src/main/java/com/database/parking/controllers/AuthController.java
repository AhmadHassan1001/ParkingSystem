package com.database.parking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.database.parking.service.UserService;
import com.database.parking.dto.LoginRequest;
import com.database.parking.dto.SignupRequestDriver;
import com.database.parking.dto.SignupRequestParkingLot;
import com.database.parking.dto.TokenResponse;
import com.database.parking.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            TokenResponse tokenResponse = userService.login(loginRequest.getName(), loginRequest.getPassword());
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/info")
    public ResponseEntity<User> info(@RequestHeader("Authorization") String token) {
      String bearerToken = token.substring(7); // Remove "Bearer " prefix
      User user = userService.getUserFromToken(bearerToken);
      return ResponseEntity.ok(user);
    }
    
    @PostMapping("/signup/driver")
    public ResponseEntity<User> signupDriver(@RequestBody SignupRequestDriver signupRequestDriver) {
        try {
            User user = userService.signupDriver(signupRequestDriver);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/signup/parking-lot")
    public ResponseEntity<User> signupParkingLotManager(@RequestBody SignupRequestParkingLot signupRequestParkingLot) {
        try {
            User user = userService.signupParkingLotManager(signupRequestParkingLot);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}