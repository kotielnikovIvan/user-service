package com.fitgoal.api.exceptions;

public class IncorrectEmailOrPasswordException extends RuntimeException {

    public IncorrectEmailOrPasswordException() {
        super("Incorrect email or password");
    }
}
