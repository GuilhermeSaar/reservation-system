package com.gstech.reservationSystem.exceptions;

import com.gstech.reservationSystem.DTO.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //AUTH

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDTO> handleBadCredentials(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseDTO("Credenciais inválidas: " + e.getMessage()));
    }


    //GLOBAL

    @ExceptionHandler(UserNotAllowedException.class)
    public ResponseEntity<ResponseDTO> handleUserNotAllowed(UserNotAllowedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleUserNotFound(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseDTO(e.getMessage()));
    }


    //Tables

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResponseDTO> handleTableAlreadyExists(ResourceAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(TableNotAvailableException.class)
    public ResponseEntity<ResponseDTO> handleTableNotAvailable(TableNotAvailableException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleTableNotFound(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(ActionNotAllowedException.class)
    public ResponseEntity<ResponseDTO> handleActionNotAllowed(ActionNotAllowedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDTO(e.getMessage()));
    }


    // RESERVATION

}
