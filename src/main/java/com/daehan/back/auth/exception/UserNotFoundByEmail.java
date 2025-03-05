package com.daehan.back.auth.exception;

import com.daehan.back.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class UserNotFoundByEmail extends CustomException {
    public UserNotFoundByEmail(String message) {
        super(
                message,
                HttpStatus.NOT_FOUND
        );
    }
}
