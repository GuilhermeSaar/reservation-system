package com.gstech.reservationSystem.exceptions;

public class TableNameAlreadyExistsException extends RuntimeException {
    public TableNameAlreadyExistsException(String message) {
        super(message);
    }
}
