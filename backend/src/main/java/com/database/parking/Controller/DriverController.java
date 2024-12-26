package com.database.parking.Controller;

import com.database.parking.DTO.DriverCreateRequest;
import com.database.parking.Entity.Driver;
import com.database.parking.Entity.User;
import com.database.parking.Service.DriverService;
import com.database.parking.Service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
// @CrossOrigin(origins = "*")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/create")
    public Long createDriver(@RequestBody DriverCreateRequest request) {
        System.out.println("Creating driver");
        long driverId=driverService.createNewUserDriver(request);
        return driverId;
    }

    @GetMapping("/greeting")
    public String greeting() {
        System.out.println("Greeting");
        return "Hello, the connection is successful!";
    }
    
}