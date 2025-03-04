package com.daehan.back.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundByEmail extends ResponseStatusException {
    public UserNotFoundByEmail(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
