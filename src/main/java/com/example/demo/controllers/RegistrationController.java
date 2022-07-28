package com.example.demo.controllers;

import com.example.demo.dtos.GeolocationResponse;
import com.example.demo.dtos.NewUserDto;
import com.example.demo.dtos.NewUserResponseDto;
import com.example.demo.exceptions.Error;
import com.example.demo.exceptions.GeoApiSearchFailed;
import com.example.demo.exceptions.InvalidCountryException;
import com.example.demo.services.GeolocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Registers a new user, if the user resides in Canada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = NewUserResponseDto.class)) }),
            @ApiResponse(responseCode = "415", description = "User DTO must be json object.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) })})
    @PostMapping(value = "/newUser", consumes = {"application/json"})
    public NewUserResponseDto registerUserAccount(@RequestBody @Valid NewUserDto new_userDto) {

        GeolocationResponse geolocationResponse = geolocationService.getUserGeolocation(new_userDto);

        return geolocationService.getUserResponse(new_userDto, geolocationResponse);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Map<String, String> handleInvalidUserDto(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCountryException.class)
    public Error handleInvalidCountryException(Exception error) {
        return new Error(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GeoApiSearchFailed.class)
    public Error handleFailedGeoApiSearch(Exception error) {
        return new Error(error);
    }
}
