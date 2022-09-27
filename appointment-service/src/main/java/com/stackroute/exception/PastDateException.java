package com.stackroute.exception;
/*This exception would be thrown when user enters past date.*/
public class PastDateException extends Exception{


    public PastDateException(String message) {
        super(message);

    }
}
