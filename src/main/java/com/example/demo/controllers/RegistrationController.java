package com.example.demo.controllers;

import com.example.demo.dtos.GeolocationResponse;
import com.example.demo.dtos.NewUserDto;
import com.example.demo.dtos.NewUserResponse;
import com.example.demo.services.GeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final GeolocationService geolocationService;

    @Autowired
    public RegistrationController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @PostMapping(value = "/newUser", consumes = {"application/xml","application/json"})
    public Mono<NewUserResponse> registerUserAccount(@RequestBody NewUserDto new_userDto) {
        geolocationService.getUserGeolocation(new_userDto);

        return null;
    }
}
