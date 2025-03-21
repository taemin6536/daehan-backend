package com.daehan.back.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionController {

//    @ExceptionHandler(UserNotFoundByEmail.class)
//    @ResponseBody
//    public ResponseEntity<Object> handleUserNotFoundByEmail(UserNotFoundByEmail ex){
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler(PasswordNotMatch.class)
//    @ResponseBody
//    public ResponseEntity<Object> handlePasswordNotMatch(PasswordNotMatch ex){
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
//    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex){
        log.error("CustomException : ", ex);
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(new ErrorResponse(ex.getMessage()));
    }

    //추가
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex){
        log.error("UsernameNotFoundException : ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage()));
    }

}
