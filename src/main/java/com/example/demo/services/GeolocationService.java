package com.example.demo.services;

import com.example.demo.dtos.GeolocationResponse;
import com.example.demo.dtos.NewUserDto;
import com.example.demo.dtos.NewUserResponseDto;
import com.example.demo.exceptions.GeoApiSearchFailed;
import com.example.demo.exceptions.InvalidCountryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class GeolocationService {

    private final String geolocation_api_url;

    private final List<String> allowedCountries;

    @Autowired
    private final RestTemplate restTemplate;

    private final String welcomeMessage;

    private static String FAIL = "fail";

    @Autowired
    public GeolocationService(@Value("${app.geolocation_api_url}") String geolocation_api_url, RestTemplate restTemplate,
                              @Value("${app.welcomeMessage}") String welcomeMessage,
                              @Value ("${app.allowedCountries}") List<String> allowedCountries){
        this.allowedCountries = allowedCountries;
        this.geolocation_api_url = geolocation_api_url;
        this.restTemplate = restTemplate;
        this.welcomeMessage = welcomeMessage;
    }

    public GeolocationResponse getUserGeolocation(NewUserDto newUserDto) {

        ResponseEntity<GeolocationResponse> response
                = restTemplate.getForEntity(geolocation_api_url + newUserDto.getIpAddress(), GeolocationResponse.class);

        Objects.requireNonNull(response.getBody());

        if (!response.getBody().getStatus().equals(FAIL)) {
            return (response.getBody());
        } else
           throw new GeoApiSearchFailed();
    }

    public NewUserResponseDto getUserResponse(NewUserDto newUserDto, GeolocationResponse response){

        NewUserResponseDto userResponseDTO;

        if(!allowedCountries.contains(response.getCountry())){
            throw new InvalidCountryException();
        }else {
            UUID uuid = UUID.randomUUID();
            userResponseDTO = new NewUserResponseDto(uuid.toString(), newUserDto.getUserName(), response.getCity(),
                    String.format(welcomeMessage, newUserDto.getUserName(), response.getCity()));
        }
        return userResponseDTO;
    }
}
