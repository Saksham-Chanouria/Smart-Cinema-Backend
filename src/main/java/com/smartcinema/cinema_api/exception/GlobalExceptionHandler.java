package com.smartcinema.cinema_api.exception;

import com.smartcinema.cinema_api.dto.SignupResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SignupResponse> handleAllExceptions(Exception ex) {

        ex.printStackTrace(); // Log error

        SignupResponse response = new SignupResponse();
        response.setSuccess(false);
        response.setMessage("Internal Server Error: " + ex.getMessage());
        response.setStatusCode(500);
        response.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<SignupResponse> handleDuplicateEmail(DataIntegrityViolationException ex) {
        SignupResponse response = new SignupResponse();
        response.setSuccess(false);
        response.setMessage("Email already exists.");
        response.setStatusCode(409); // Conflict
        response.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
