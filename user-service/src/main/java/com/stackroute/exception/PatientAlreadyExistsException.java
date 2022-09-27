package com.stackroute.exception;

public class PatientAlreadyExistsException extends RuntimeException {

    public PatientAlreadyExistsException(String message) {
        super(message);
    }
}