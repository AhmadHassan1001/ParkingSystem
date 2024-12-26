package com.database.parking.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.database.parking.Service.UserService;
import com.database.parking.dto.LoginRequest;
import com.database.parking.dto.SignupRequestDriver;
import com.database.parking.dto.SignupRequestParkingLot;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private UserService userService = new UserService();

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String tokenResponse = userService.login(loginRequest);
        if (tokenResponse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/signup/driver")
    public ResponseEntity<String> signupDriver(@RequestBody SignupRequestDriver signupRequestDriver) {
        String tokenResponse = userService.signupDriver(signupRequestDriver);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/signup/parking-lot")
    public ResponseEntity<String> signupParkingLotManager(@RequestBody SignupRequestParkingLot signupRequestParkingLot) {
        String tokenResponse = userService.signupParkingLotManager(signupRequestParkingLot);
        return ResponseEntity.ok(tokenResponse);
    }
}