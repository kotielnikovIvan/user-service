package com.fitgoal.api.exceptions;

public class IncorrectEmailOrPasswordException extends RuntimeException {

    public IncorrectEmailOrPasswordException(String message) {
        super(message);
    }
}
