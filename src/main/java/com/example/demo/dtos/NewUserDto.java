package com.example.demo.dtos;

import com.example.demo.validators.NewUserDtoConstraint;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Schema(description = "New user Dto")
public class NewUserDto {

    @NotBlank
    private final String userName;

    @NotBlank
    @NewUserDtoConstraint
    private final String password;

    @NotBlank
    private final String ipAddress;

    public NewUserDto(String userName, String password, String ipAddress) {
        this.userName = userName;
        this.password = password;
        this.ipAddress = ipAddress;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
