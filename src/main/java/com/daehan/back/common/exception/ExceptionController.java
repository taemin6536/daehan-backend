package com.daehan.back.common.exception;

import com.daehan.back.auth.exception.PasswordNotMatch;
import com.daehan.back.auth.exception.UserNotFoundByEmail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserNotFoundByEmail.class)
    @ResponseBody
    public ResponseEntity<Object> handleUserNotFoundByEmail(UserNotFoundByEmail ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordNotMatch.class)
    @ResponseBody
    public ResponseEntity<Object> handlePasswordNotMatch(PasswordNotMatch ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
