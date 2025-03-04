package com.daehan.back.auth.exception;

import com.daehan.back.common.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PasswordNotMatch extends CustomException {
    public PasswordNotMatch(String message) {
        super(
                message,
                HttpStatus.UNAUTHORIZED
        );
    }
}
