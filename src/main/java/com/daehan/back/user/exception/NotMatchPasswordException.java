package com.daehan.back.user.exception;

public class NotMatchPasswordException extends RuntimeException {
    public NotMatchPasswordException(String message) {
        super(message);
    }
}
