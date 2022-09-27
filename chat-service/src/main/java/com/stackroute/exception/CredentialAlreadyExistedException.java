package com.stackroute.exception;

public class CredentialAlreadyExistedException extends RuntimeException{
    public CredentialAlreadyExistedException(String message){
        super(message);
    }
}
