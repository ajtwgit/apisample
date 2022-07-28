package com.example.demo;

import com.example.demo.dtos.GeolocationResponse;
import com.example.demo.dtos.NewUserDto;
import com.example.demo.dtos.NewUserResponseDto;
import com.example.demo.exceptions.GeoApiSearchFailed;
import com.example.demo.exceptions.InvalidCountryException;
import com.example.demo.services.GeolocationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ApiSampleApplicationTests {

    @Mock
    RestTemplate restTemplate;

    @Value("${app.geolocation_api_url}")
    String geolocation_api_url;

    @Value("${app.welcomeMessage}")
    String welcomeMessage;

    @Value ("${app.allowedCountries}")
    List<String> allowedCountries;

    @Test
    void shouldPassWhenDtoHasValidDataInDto() {

        NewUserDto user = new NewUserDto("tubbs", "s2sSsssd#d", "198.90.81.246");
        GeolocationService geolocationService = new GeolocationService(geolocation_api_url, restTemplate,
                welcomeMessage, allowedCountries);

        final GeolocationResponse response = new GeolocationResponse();
        response.setCity("Lunenburg");
        response.setStatus("success");
        response.setCountry("Canada");

        Mockito.when(restTemplate.getForEntity(geolocation_api_url + user.getIpAddress(), GeolocationResponse.class))
                .thenReturn(new ResponseEntity(response, HttpStatus.OK));

        GeolocationResponse geolocationResponse = geolocationService.getUserGeolocation(user);
        NewUserResponseDto newUserResponseDto  = geolocationService.getUserResponse(user, geolocationResponse);

        assertThat(newUserResponseDto.getUserName()).isEqualTo("tubbs");
        assertThat(newUserResponseDto.getWelcomeMessage()).isNotEmpty();
        assertThat(newUserResponseDto.getUuid()).isNotEmpty();
    }
    @Test
    void shouldNotPassWhenDtoHasInvalidIpAddress() {
        // ipAddress param should not include port
        final NewUserDto user =
                new NewUserDto("aaron", "s2sSsssd#d", "198.90.81.246:80");

        GeolocationService geolocationService = new GeolocationService(geolocation_api_url, restTemplate,
                welcomeMessage, allowedCountries);

        final GeolocationResponse response = new GeolocationResponse();
        response.setCity("Lunenburg");
        response.setStatus("fail");
        response.setCountry("Canada");

        Mockito.when(restTemplate.getForEntity(geolocation_api_url + user.getIpAddress(), GeolocationResponse.class))
                .thenReturn(new ResponseEntity(response, HttpStatus.OK));


        Exception exception = assertThrows(GeoApiSearchFailed.class, () -> {
            GeolocationResponse geolocationResponse = geolocationService.getUserGeolocation(user);
        });

        String geoApiSearchFailedMessage = "Bad data sent to API";
        String exceptionMessage = exception.getMessage();
        assertTrue(exceptionMessage.contains(geoApiSearchFailedMessage));
    }

    @Test
    void shouldThrowExceptionWhenUserIsLocatedInNonValidCountry() {

        final NewUserDto user =
                new NewUserDto("aaron", "ss2Ssssd#d", "104.236.232.28");

        GeolocationService geolocationService = new GeolocationService(geolocation_api_url, restTemplate,
                welcomeMessage, allowedCountries);

        final GeolocationResponse response = new GeolocationResponse();
        response.setCity("Philadelphia");
        response.setStatus("success");
        response.setCountry("US");

        Mockito.when(restTemplate.getForEntity(geolocation_api_url + user.getIpAddress(), GeolocationResponse.class))
                .thenReturn(new ResponseEntity(response, HttpStatus.OK));

        GeolocationResponse geolocationResponse = geolocationService.getUserGeolocation(user);
        Exception exception = assertThrows(InvalidCountryException.class, () -> {
            NewUserResponseDto newUserResponseDto  =
                    geolocationService.getUserResponse(user, geolocationResponse);

        });

        String geoApiSearchFailedMessage = "User IP address must be Canadian";
        String exceptionMessage = exception.getMessage();
        assertTrue(exceptionMessage.contains(geoApiSearchFailedMessage));
    }

}
