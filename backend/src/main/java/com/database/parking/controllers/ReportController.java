package com.database.parking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.database.parking.dto.ReportPaths;
import com.database.parking.models.User;
import com.database.parking.service.ReportService;
import com.database.parking.service.UserService;

@RestController
@RequestMapping()
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @GetMapping("/reports/admin-dashboard")
    public ResponseEntity<ReportPaths> getAdminDashboardReport() {
        try {
            String[] reports = reportService.generateAdminDashboardReport();
            if (reports == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }

            return ResponseEntity.ok(new ReportPaths(reports[0], reports[1]));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/reports/manager-dashboard")
    public ResponseEntity<String> getManagerDashboardReport(@RequestHeader("Authorization") String token) {
        try {
            String bearerToken = token.substring(7); // Remove "Bearer " prefix
            System.out.println(bearerToken);
            User user = userService.getUserFromToken(bearerToken);
            String report = reportService.generateManagerDashboardReport(user.getId());
            if (report == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }

            return ResponseEntity.ok(report);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}