package com.stackroute.exceptions;

public class EmailNotFoundException extends RuntimeException{

    String message;

    public EmailNotFoundException(String message){
        super(message);
    }

}
