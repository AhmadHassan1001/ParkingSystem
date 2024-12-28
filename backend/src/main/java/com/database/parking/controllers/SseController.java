package com.database.parking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.database.parking.models.User;
import com.database.parking.service.SseService;
import com.database.parking.service.UserService;

@RestController
public class SseController {

    @Autowired
    private UserService userService;

    @Autowired
    private SseService sseService;
    
    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sse(@RequestHeader("Authorization") String token) {
        String bearerToken = token.substring(7); // Remove "Bearer " prefix
        User user = userService.getUserFromToken(bearerToken);
        long userId = user.getId();

        SseEmitter emitter = new SseEmitter();

        // add emmiter 
        sseService.addEmitter(userId, emitter);
        return emitter;
    }

}
