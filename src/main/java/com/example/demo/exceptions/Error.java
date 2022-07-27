package com.example.demo.exceptions;

public class Error {
    public final String ex;
    public Error(Exception ex) {
        this.ex = ex.getLocalizedMessage();
    }
}
