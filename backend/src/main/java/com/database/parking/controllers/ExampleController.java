package com.database.parking.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.database.parking.models.User;
import com.database.parking.orm.MySQLConnection;
import com.database.parking.orm.Repo;


@RestController
public class ExampleController {

    @Value("${app.title}") // Injecting the value of app.greeting from application.properties
    private String greeting;


    @GetMapping("/greet")
    public String greet() {
        Repo repo = new Repo(new User());
        List<User> users = repo.all();
        for(User user : users) {
            System.out.println(user);
        }
        return repo.all().toString();

        // return greeting; // Return the value of app.greeting
    }
}