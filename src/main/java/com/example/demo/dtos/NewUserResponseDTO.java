package com.example.demo.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Schema(description = "New user Response")
public class NewUserResponseDTO {

    @NotBlank
    private String uuid;

    @NotBlank
    private final String userName;

    @NotBlank
    private final String city;

    @NotBlank
    private final String welcomeMessage;

    public NewUserResponseDTO(String uuid, String userName, String city, String welcomeMessage) {
        this.uuid = uuid;
        this.userName = userName;
        this.city = city;
        this.welcomeMessage = welcomeMessage;
    }

    public String getUuid() {
        return uuid;
    }

    public String getUserName() {
        return userName;
    }

    public String getCity() {
        return city;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    @Override
    public String toString() {
        return "NewUserResponseDTO{" +
                "uuid='" + uuid + '\'' +
                ", userName='" + userName + '\'' +
                ", city='" + city + '\'' +
                ", welcomeMessage='" + welcomeMessage + '\'' +
                '}';
    }
}
