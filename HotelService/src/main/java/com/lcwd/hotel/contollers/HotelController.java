package com.lcwd.hotel.contollers;

import com.lcwd.hotel.entities.Hotel;
import com.lcwd.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;


    @PostMapping
    public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel){
        return  ResponseEntity.status(HttpStatus.CREATED).body(hotelService.saveHotel(hotel));
    }






    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> saveHotel(@PathVariable String hotelId){
        return  ResponseEntity.status(HttpStatus.FOUND).body(hotelService.getSingleHotel(hotelId));
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels(){
        return ResponseEntity.ok(hotelService.getAllHotels());
    }



}
