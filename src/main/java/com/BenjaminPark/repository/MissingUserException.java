package com.BenjaminPark.repository;

public class MissingUserException extends Exception {
    public MissingUserException(String message) {
        super(message);
    }
}
