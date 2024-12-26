package com.database.parking.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.database.parking.service.UserService;
import com.database.parking.dto.LoginRequest;
import com.database.parking.dto.SignupRequestDriver;
import com.database.parking.dto.SignupRequestParkingLot;
import com.database.parking.dto.TokenResponse;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = userService.login(loginRequest.getName(), loginRequest.getPassword());
        return ResponseEntity.ok(tokenResponse.getToken());
    }

    @PostMapping("/signup/driver")
    public ResponseEntity<String> signupDriver(@RequestBody SignupRequestDriver signupRequestDriver) {
        TokenResponse tokenResponse = userService.signupDriver(signupRequestDriver);
        return ResponseEntity.ok(tokenResponse.getToken());
    }

    @PostMapping("/signup/parking-lot")
    public ResponseEntity<String> signupParkingLotManager(@RequestBody SignupRequestParkingLot signupRequestParkingLot) {
        TokenResponse tokenResponse = userService.signupParkingLotManager(signupRequestParkingLot);
        return ResponseEntity.ok(tokenResponse.getToken());
    }
}