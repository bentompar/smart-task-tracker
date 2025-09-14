package com.BenjaminPark.service;

/**
 * Custom exception for when methods evoked on missing class.
 * Presumably a mistake when passing the taskId has happened.
 */
public class MissingTaskException extends Exception {
    public MissingTaskException(String message) {
        super(message);
    }
}