package com.lcwd.rating.RatingService.repositories;

import com.lcwd.rating.RatingService.entities.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepo extends MongoRepository<Rating,String> {
}
