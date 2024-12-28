package com.database.parking.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.database.parking.dao.NotificationDAO;
import com.database.parking.models.Notification;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
// import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseService {

    @Autowired
    NotificationDAO notificationDAO;

    private final Map<Long, SseEmitter> emitters = new HashMap<>();
    
    public void addEmitter(long userId, SseEmitter emitter) {
        emitters.put(userId, emitter);
        
    }

    @Scheduled(fixedRate = 1000)
    public void sendEvents() throws Exception {
        for (Map.Entry<Long, SseEmitter> entry : emitters.entrySet()) {
            try {
                long userId = entry.getKey();
                List<Notification> notifications = notificationDAO.getNTopNotifications(userId, 5);
                entry.getValue().send(notifications);
            } catch (Exception e) {
                emitters.remove(entry.getKey());
                throw e;
            }
        }
    }

}
