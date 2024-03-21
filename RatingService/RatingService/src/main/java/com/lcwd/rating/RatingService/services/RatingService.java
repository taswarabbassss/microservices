package com.lcwd.rating.RatingService.services;

import com.lcwd.rating.RatingService.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RatingService {

    Rating createRating(Rating rating);
    List<Rating> getAllRating();
    List<Rating> getAllRatingByUserId(String userId);
    List<Rating> getAllRatingByHotelId(String hotelId);


}
