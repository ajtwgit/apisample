package com.example.demo.services;

import com.example.demo.dtos.GeolocationResponse;
import com.example.demo.dtos.NewUserDto;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeolocationService {

    private  final String geolocation_api_url;

    @Autowired
    public GeolocationService(@Value("${app.geolocation_api_url}") String geolocation_api_url){
        this.geolocation_api_url = geolocation_api_url;
    }

    public Mono<GeolocationResponse> getUserGeolocation(NewUserDto newUserDto){

        WebClient client = WebClient.create(geolocation_api_url);
        client.get()
                .uri("/json/", newUserDto.getIpAddress()).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GeolocationResponse.class)
                .subscribe(geolocationResponse ->
                                System.out.println(geolocationResponse.getCity()),
                                error -> System.err.println("Error: " + error),
                                        () -> System.out.println("done"));
        return null;
    }

}
