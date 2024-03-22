package com.lcwd.user.service.UserService.services.Impl;

import com.lcwd.user.service.UserService.entities.Hotel;
import com.lcwd.user.service.UserService.entities.Rating;
import com.lcwd.user.service.UserService.entities.User;
import com.lcwd.user.service.UserService.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.UserService.repositories.UserRepository;
import com.lcwd.user.service.UserService.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Rating[] ratings = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+userId, Rating[].class);
        List<Rating> ratingsList = Arrays.stream(ratings).toList();
        user.setRatings(ratingsList);
        logger.info("[RATINGS]", ratings);

        //GET HOTEL OF EVERY RATING
//        http://localhost:8082/hotels/f46fa7a6-51cd-4ff6-a4b5-e84a11c45334
        ratingsList.stream().map( rating -> {

        ResponseEntity<Hotel> hotelResponseEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+ rating.getHotelId(), Hotel.class);
        Hotel hotel = hotelResponseEntity.getBody();
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());



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
