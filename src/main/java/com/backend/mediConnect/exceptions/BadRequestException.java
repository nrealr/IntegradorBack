package com.backend.mediConnect.exceptions;

public class BadRequestException extends Exception{
    public BadRequestException(String message){
        super(message);
    }
}
