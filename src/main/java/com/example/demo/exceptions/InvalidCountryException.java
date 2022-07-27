package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidCountryException extends RuntimeException{
    final static String MESSAGE = "User IP address must be Canadian";
    public InvalidCountryException() {
        super(MESSAGE);
    }
}
