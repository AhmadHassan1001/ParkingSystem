package com.database.parking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.database.parking.dao.UserDAO;
import com.database.parking.models.Driver;
import com.database.parking.models.User;

@Service
public class UserService {

    private UserDAO userDAO = new UserDAO();

    public void save(User user) {
        userDAO.save(user);
    }

    public void update(User user) {
        userDAO.update(user);
    }

    public void delete(long id) {
        userDAO.delete(id);
    }

    public User getById(long id) {
        return userDAO.getById(id);
    }

    public List<User> getAll() {
        return userDAO.getAll();
    }


}