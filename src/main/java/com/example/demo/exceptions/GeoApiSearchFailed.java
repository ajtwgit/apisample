package com.example.demo.exceptions;

public class GeoApiSearchFailed extends RuntimeException{
    final static String MESSAGE = "Bad data sent to API";
    public GeoApiSearchFailed() {
        super(MESSAGE);
    }
}