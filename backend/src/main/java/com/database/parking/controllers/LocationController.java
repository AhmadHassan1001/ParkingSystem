
package com.database.parking.controllers;

import com.database.parking.models.Location;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.database.parking.dao.LocationDAO;




@RestController
@RequestMapping("/locations")
@CrossOrigin
public class LocationController {
    
    @Autowired
    private LocationDAO locationDAO;

    @GetMapping
    public List<Location> getLocations() {
        try {
            return locationDAO.getAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching locations");
        }
    }


}
