package com.sparta.postingboard.exception;


import com.sparta.postingboard.dto.StatusCodeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<StatusCodeDto> handleException(IllegalArgumentException ex) {
        StatusCodeDto restApiException = new StatusCodeDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<StatusCodeDto> handleException(MethodArgumentNotValidException ex) {
        StatusCodeDto restApiException = new StatusCodeDto(HttpStatus.BAD_REQUEST.value(), ex.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}

