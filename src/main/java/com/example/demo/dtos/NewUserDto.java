package com.example.demo.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Schema(description = "New user Dto")
public class NewUserDto {

    @NotBlank
    private final String userName;

    @Size(min=8, max=14)
    private final String password;

    @NotBlank
    private final String IpAddress;

    public NewUserDto(String userName, String password, String ipAddress) {
        this.userName = userName;
        this.password = password;
        IpAddress = ipAddress;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getIpAddress() {
        return IpAddress;
    }
}
