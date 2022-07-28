package com.example.demo.exceptions;

public class InvalidCountryException extends RuntimeException{
    final static String MESSAGE = "User IP address must be Canadian";
    public InvalidCountryException() {
        super(MESSAGE);
    }
}
