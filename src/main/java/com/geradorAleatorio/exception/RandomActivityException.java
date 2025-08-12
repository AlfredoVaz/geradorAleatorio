package com.geradorAleatorio.exception;

public class RandomActivityException extends RuntimeException {
    public RandomActivityException(String message) {
        super(message);
    }

    public RandomActivityException(String message, Throwable cause) {
        super(message, cause);
    }
}


