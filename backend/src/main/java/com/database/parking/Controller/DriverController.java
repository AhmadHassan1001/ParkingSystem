package com.database.parking.Controller;

import com.database.parking.Entity.Driver;
import com.database.parking.Entity.User;
import com.database.parking.Service.DriverService;
import com.database.parking.Service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin(origins = "*")
public class DriverController {

    private final DriverService driverService;

    public DriverController(UserService userService, DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/create")
    public Long createDriver(@RequestBody User user, @RequestBody Driver driver) {
        driverService.saveDriverUser(driver, user);
        return driver.getUserId();
    }
}