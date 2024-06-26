package com.lcwd.user.service.UserService.controllers;

import com.lcwd.user.service.UserService.entities.User;
import com.lcwd.user.service.UserService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<User> getSingleUser(@PathVariable("userId") String userId) {
        User userById = userService.getUserById(userId);
        return ResponseEntity.ok(userById);
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable String userId){
       String response =  userService.deleteUserById(userId);
       return  ResponseEntity.ok(response);
    }

}
