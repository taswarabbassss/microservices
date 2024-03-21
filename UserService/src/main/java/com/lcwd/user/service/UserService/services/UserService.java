package com.lcwd.user.service.UserService.services;

import com.lcwd.user.service.UserService.entities.User;

import java.util.List;

public interface UserService {

    // USER OPERATIONS

    //create
    User saveUser(User user);

    //get all users
    List<User> getAllUsers();
    //get single user by id
    User getUserById(String userId);

    String deleteUserById(String userId);
    // update
}
