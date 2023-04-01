package com.example.moa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Object> handleDuplicateEmailException(DuplicateEmailException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errorCode", "DUPLICATE_EMAIL");
        errorResponse.put("errorMessage", "이미 등록된 이메일입니다.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handlerUsernameNotFoundException(UsernameNotFoundException ex){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errorCode", "NOT_FOUND_EMAIL");
        errorResponse.put("errorMessage", "등록되지 않은 email 입니다.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handlerBadCredentialsException(BadCredentialsException ex){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errorCode", "INVALID_PASSWORD");
        errorResponse.put("errorMessage", "비밀번호가 일치하지 않습니다.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errorCode", "INTERNAL_SERVER_ERROR");
        errorResponse.put("errorMessage", "서버 내부 오류가 발생했습니다.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
