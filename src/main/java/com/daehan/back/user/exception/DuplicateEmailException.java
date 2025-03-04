package com.daehan.back.user.exception;

import com.daehan.back.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends CustomException {
    public DuplicateEmailException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
