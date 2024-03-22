package com.lcwd.user.service.UserService.services.Impl;

import com.lcwd.user.service.UserService.entities.User;
import com.lcwd.user.service.UserService.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.UserService.repositories.UserRepository;
import com.lcwd.user.service.UserService.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);



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
        // GET USER FROM DATABASE
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given ID: " + userId + " not found on the server!!!"));

        // GET RATINGS ACCORDING TO USER ID WITH THE HELP OF RATINGS MICRO SERVICE
        //http://localhost:8083/ratings/users/2d51915e-b48d-41a8-89d0-5c2f3e1620c4


        ArrayList ratings = restTemplate.getForObject("http://localhost:8083/ratings/users/"+userId, ArrayList.class);
        user.setRatings(ratings);
        logger.info("[RATINGS]", ratings);
        return  user;
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
