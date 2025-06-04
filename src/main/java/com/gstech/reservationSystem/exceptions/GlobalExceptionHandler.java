package com.gstech.reservationSystem.exceptions;

import com.gstech.reservationSystem.DTO.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ResponseDTO> handleEmailAlreadyExists(EmailAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(TableNameAlreadyExistsException.class)
    public ResponseEntity<ResponseDTO> handleTableAlreadyExists(EmailAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO("Internal server error: " + ex.getMessage()));
    }

}
