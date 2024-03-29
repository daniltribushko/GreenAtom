package com.example.GreenAtom.controllers;

import com.example.GreenAtom.exceptions.AppException;
import com.example.GreenAtom.models.dto.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 *
 * Контроллер для перехвата исключений
 */
@ControllerAdvice
public class AdviceExceptionController {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ExceptionResponse> appExceptionHandler(AppException exception){
        return ResponseEntity.status(exception.getStatus()).body(new ExceptionResponse(exception.getStatus(),
                exception.getTimestamp(),
                exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> validExceptionHandler(MethodArgumentNotValidException exception){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(new ExceptionResponse(status.value(),
                LocalDateTime.now(),
                exception.getMessage()));
    }
}
