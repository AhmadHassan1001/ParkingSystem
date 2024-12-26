package com.database.parking.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.database.parking.Service.UserService;
import com.database.parking.dto.LoginRequest;
import com.database.parking.dto.SignupRequestDriver;
import com.database.parking.dto.TokenResponse;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = userService.login(loginRequest.getName(), loginRequest.getPassword());
        if (tokenResponse != null) {
            return ResponseEntity.ok(tokenResponse);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/signup/driver")
    public ResponseEntity<String> signupDriver(@RequestBody SignupRequestDriver signupRequestDriver) {
        TokenResponse tokenResponse = userService.signupDriver(signupRequestDriver);
        String token = tokenResponse.gettoken();
        return ResponseEntity.ok(tokenResponse..toString());
    }

    // @PostMapping("/signup/parking-lot")
    // public ResponseEntity<TokenResponse> signupParkingLotManager(@RequestBody SignupRequest signupRequest) {
    //     signupRequest.setRole(Role.MANAGEMENT);
    //     TokenResponse tokenResponse = userService.signup(signupRequest);
    //     return ResponseEntity.ok(tokenResponse);
    // }
}