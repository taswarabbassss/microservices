package com.lcwd.user.service.UserService.exeptions;

import com.lcwd.user.service.UserService.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.UserService.payloads.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<ApiResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException exception){
        String message = exception.getMessage();

    }
}
