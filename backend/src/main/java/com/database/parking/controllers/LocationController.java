
package com.database.parking.controllers;

import com.database.parking.models.Location;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.database.parking.dao.LocationDAO;




@RestController
@RequestMapping("/location")
@CrossOrigin
public class LocationController {
    
    @Autowired
    private LocationDAO locationDAO;

    @GetMapping
    public List<Location> getLocations() {
        return locationDAO.getAll();
    }


}
