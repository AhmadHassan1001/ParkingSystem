package com.database.parking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.database.parking.models.User;
import com.database.parking.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SseController {

    @Autowired
    private UserService userService;
    
    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sse(@RequestHeader("Authorization") String token) {
        String bearerToken = token.substring(7); // Remove "Bearer " prefix
        User user = userService.getUserFromToken(bearerToken);

        SseEmitter emitter = new SseEmitter();
        // add emmiter 
        
        
        return emitter;
    }

}
