package com.lcwd.user.service.UserService.services.Impl;

import com.lcwd.user.service.UserService.entities.User;
import com.lcwd.user.service.UserService.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.UserService.repositories.UserRepository;
import com.lcwd.user.service.UserService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public User saveUser(User user) {
        String uniqueUserId = UUID.randomUUID().toString();
    user.setUserId(uniqueUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given ID: " + userId + " not found on the server!!!"));
    }

    @Override
    public String deleteUserById(String userId) {
        try{

        userRepository.deleteById(userId);
        } catch (Exception ex){
            throw new RuntimeException("Delete unsuccessful");
        }
        return "Deleted Successfully";
    }
}
