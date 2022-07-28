package com.example.demo;

import com.example.demo.dtos.GeolocationResponse;
import com.example.demo.dtos.NewUserDto;
import com.example.demo.dtos.NewUserResponseDto;
import com.example.demo.services.GeolocationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class ApiSampleApplicationTests {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private static GeolocationService service;

	@Value("${app.geolocation_api_url}")
	 String geolocation_api_url;

	@Value("${app.welcomeMessage}")
	String welcomeMessage;

	@Test
	void contextLoads() {

		final NewUserDto user = new NewUserDto("aaron", "s2sSsssd#d", "198.90.81.246");
//
		final GeolocationResponse response = new GeolocationResponse();
		response.setCity("Lunenburg");
		response.setStatus("success");
		response.setCountry("Canada");

		Mockito.when(restTemplate.getForEntity(geolocation_api_url + user.getIpAddress(), GeolocationResponse.class))
          .thenReturn(new ResponseEntity(response, HttpStatus.OK));

		Optional<GeolocationResponse> geolocationResponse = service.getUserGeolocation(user);

		NewUserResponseDto newUserResponseDto;

		if(geolocationResponse.isPresent()){
			newUserResponseDto = service.getUserResponse(user, geolocationResponse.get());
		}
	}

}
