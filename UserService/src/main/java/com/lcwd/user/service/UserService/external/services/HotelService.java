package com.lcwd.user.service.UserService.external.services;

import com.lcwd.user.service.UserService.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name= "HOTEL-SERVICE")
public interface HotelService {
    @GetMapping("/hotels/{hotelId}")
    ResponseEntity<Hotel> getHotel(@PathVariable("hotelId") String hotelId);
    @GetMapping("/hotels")
    ResponseEntity<List<Hotel>> getAllHotels();

}
