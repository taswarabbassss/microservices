package com.lcwd.hotel.services;


import com.lcwd.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    //create
    Hotel saveHotel(Hotel hotel);
    //get all
    List<Hotel> getAllHotels();
    //get by id
    Hotel getSingleHotel(String hotelId);
}
