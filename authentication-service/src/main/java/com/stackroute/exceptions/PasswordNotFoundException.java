package com.stackroute.exceptions;



public class PasswordNotFoundException extends RuntimeException{

    String message;

    public PasswordNotFoundException(String message) {
        super(message);
    }
}
