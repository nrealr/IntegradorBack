package com.backend.mediConnect.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)

    public Map<String, String> handleResourceNotFound(ResourceNotFoundException exception){
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("message", "Resource not found: " + exception.getMessage());
        return mensaje;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)

    public Map<String, String> processValidationException(MethodArgumentNotValidException exception){
        Map<String, String> exceptionMessage = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            exceptionMessage.put(fieldName, errorMessage);
        });

        return exceptionMessage;

    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequests(BadRequestException exception){
        Map<String, String> mensaje = new HashMap<>();

        mensaje.put("message", "Bad Request: " + exception.getMessage());
        return mensaje;
    }
}
