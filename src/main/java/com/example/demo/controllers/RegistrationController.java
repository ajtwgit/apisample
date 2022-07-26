package com.example.demo.controllers;

import com.example.demo.dtos.GeolocationResponse;
import com.example.demo.dtos.NewUserDto;
import com.example.demo.dtos.NewUserResponseDto;
import com.example.demo.services.GeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final GeolocationService geolocationService;

    @Autowired
    public RegistrationController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @PostMapping(value = "/newUser", consumes = {"application/xml","application/json"})
    public NewUserResponseDto registerUserAccount(@RequestBody @Valid NewUserDto new_userDto) {
         final String DEFAULT_STATUS = "Unknown";

        GeolocationResponse geolocationResponse = geolocationService.getUserGeolocation(new_userDto).orElseThrow(
                () -> new NotFoundException("Employee not found with id"));

        return geolocationService.getUserResponse(new_userDto, geolocationResponse);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidUseDto(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
