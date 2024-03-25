package com.lcwd.user.service.UserService.services.Impl;

import com.lcwd.user.service.UserService.entities.Hotel;
import com.lcwd.user.service.UserService.entities.Rating;
import com.lcwd.user.service.UserService.entities.User;
import com.lcwd.user.service.UserService.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.UserService.external.services.HotelService;
import com.lcwd.user.service.UserService.repositories.UserRepository;
import com.lcwd.user.service.UserService.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private HotelService hotelClientService;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


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
        Rating[] ratings = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + userId, Rating[].class);
        List<Rating> ratingsList = Arrays.stream(ratings).toList();
        user.setRatings(ratingsList);
        logger.info("[RATINGS]", ratings);

        //GET HOTEL OF EVERY RATING
//        http://localhost:8082/hotels/f46fa7a6-51cd-4ff6-a4b5-e84a11c45334
        ratingsList.stream().map(rating -> {

//       ResponseEntity<Hotel> hotelResponseEntity = restTemplate.getForEntity("http://localhost:8082/hotels/"+ rating.getHotelId(), Hotel.class);
//       Hotel hotel = hotelResponseEntity.getBody();

               Hotel hotel = hotelClientService.getHotel(rating.getHotelId()).getBody();
              rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());
//        try {

//            Hotel hotel = this.hotelClientService.getHotel("0f314e16-014b-4b7e-9713-5829efcf565e").getBody();
//            System.out.println(hotel);

//            List<Hotel> allHotels = hotelClientService.getAllHotels().getBody();
//            allHotels.forEach(hotel -> System.out.println(hotel));
//            System.out.println(allHotels.size());
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }


        return user;
    }

    @Override
    public String deleteUserById(String userId) {
        try {

            userRepository.deleteById(userId);
        } catch (Exception ex) {
            throw new RuntimeException("Delete unsuccessful");
        }
        return "Deleted Successfully";
    }
}
