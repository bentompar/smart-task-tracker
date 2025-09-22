package com.BenjaminPark.repository;

public class DuplicateUserNameException extends Exception {
    public DuplicateUserNameException(String message) {
        super(message);
    }
}
