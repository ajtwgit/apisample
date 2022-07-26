package com.example.demo.services;

import com.example.demo.dtos.GeolocationResponse;
import com.example.demo.dtos.NewUserDto;
import com.example.demo.dtos.NewUserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.webjars.NotFoundException;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class GeolocationService {

    private final String geolocation_api_url;
    private final RestTemplate restTemplate;
    private final String welcomeMessage;

    @Autowired
    public GeolocationService(@Value("${app.geolocation_api_url}") String geolocation_api_url, RestTemplate restTemplate,
                              @Value("${app.welcomeMessage}") String welcomeMessage){
        this.geolocation_api_url = geolocation_api_url;
        this.restTemplate = restTemplate;
        this.welcomeMessage = welcomeMessage;
    }

    public Optional<GeolocationResponse> getUserGeolocation(NewUserDto newUserDto) {

        ResponseEntity<GeolocationResponse> response
                = restTemplate.getForEntity(geolocation_api_url + newUserDto.getIpAddress(), GeolocationResponse.class);

        Objects.requireNonNull(response.getBody());

        if (!response.getBody().getStatus().equals("fail")) {
            return Optional.of(response.getBody());
        } else
            return Optional.empty();
    }

    public NewUserResponseDto getUserResponse(NewUserDto newUserDto, GeolocationResponse response){

        NewUserResponseDto userResponseDTO;

        if(!response.getCountry().equals("Canada")){
            throw new NotFoundException("Employee not found with id");
        }else {
            UUID uuid = UUID.randomUUID();
            userResponseDTO = new NewUserResponseDto(uuid.toString(), newUserDto.getUserName(), response.getCity(),
                    String.format(welcomeMessage, newUserDto.getUserName(), response.getCity()));
        }

        return userResponseDTO;
    }
}
