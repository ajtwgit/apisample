package com.example.demo.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Schema(description = "New user Response")
public class NewUserResponse {

    @NotBlank
    private String uuid;

    @NotBlank
    private String userName;

    @NotBlank
    private String city;

    @NotBlank
    private String welcomeMessage;


}
