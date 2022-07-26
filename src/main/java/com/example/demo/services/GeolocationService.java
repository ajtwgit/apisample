package com.example.demo.services;

import com.example.demo.dtos.GeolocationResponse;
import com.example.demo.dtos.NewUserDto;
import com.example.demo.dtos.NewUserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Autowired
    public GeolocationService(@Value("${app.geolocation_api_url}") String geolocation_api_url, RestTemplate restTemplate){
        this.geolocation_api_url = geolocation_api_url;
        this.restTemplate = restTemplate;
    }

    public Optional<GeolocationResponse> getUserGeolocation(NewUserDto newUserDto) {

        ResponseEntity<GeolocationResponse> response
                = restTemplate.getForEntity(geolocation_api_url + "ss" + newUserDto.getIpAddress(), GeolocationResponse.class);

        Objects.requireNonNull(response.getBody());

        if (!response.getBody().getStatus().equals("fail")) {
            return Optional.of(response.getBody());
        } else
            return Optional.empty();
    }

    public NewUserResponseDTO getUserResponse(NewUserDto newUserDto, GeolocationResponse response){

        NewUserResponseDTO userResponseDTO;
        if(!response.getCountry().equals("Canada")){
            throw new NotFoundException("Employee not found with id");
        }else {
            UUID uuid = UUID.randomUUID();

            userResponseDTO = new NewUserResponseDTO(uuid.toString(), newUserDto.getUserName(), response.getCity(),"ss");
        }

        return userResponseDTO;
    }
}
