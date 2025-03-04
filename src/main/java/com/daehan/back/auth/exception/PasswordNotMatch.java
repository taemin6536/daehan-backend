package com.daehan.back.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PasswordNotMatch extends ResponseStatusException {
    public PasswordNotMatch(String message) {
        super(
                HttpStatus.UNAUTHORIZED, message
        );
    }
}
