package com.library_system.Library_System.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation error");

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            apiError.addValidationError(error.getField(), error.getDefaultMessage());
        }

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFound(
            ResourceNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AiServiceException.class)
    protected ResponseEntity<Object> handleAiServiceException(
            AiServiceException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "AI Service error: " + ex.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllOtherExceptions(
            Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred"
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}