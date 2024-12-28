package com.database.parking.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.database.parking.dao.NotificationDAO;
import com.database.parking.models.Notification;
import com.database.parking.models.User;
import com.database.parking.service.UserService;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {
    
    @Autowired
    private NotificationDAO notificationDAO;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Notification> getNotifications( @RequestHeader("Authorization") String token) {
        try {
            User user = userService.getUserFromToken(token);
            return notificationDAO.getByUserId(user.getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching notifications");
        }
    }
}
