package com.lcwd.rating.RatingService.repositories;

import com.lcwd.rating.RatingService.entities.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepo extends MongoRepository<Rating,String> {
    //CREATE CUSTOM METHOD TO FIND BY USER AND HOTEL ID

    List<Rating> findByUserId(String userId);
    List<Rating> findByHotelId(String hotelId);
}
