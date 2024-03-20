package com.lcwd.user.service.UserService.exceptions;

public class ResourceNotFoundException extends RuntimeException{


    // YOU CAN ADD EXTRA PROPERTIES YOU WANT TO MANAGE
    public ResourceNotFoundException(){
        super("Resource not found on the server!!!");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
