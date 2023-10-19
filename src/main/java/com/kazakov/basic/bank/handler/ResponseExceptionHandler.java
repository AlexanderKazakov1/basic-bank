package com.kazakov.basic.bank.handler;

import com.kazakov.basic.bank.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(MethodArgumentNotValidException ex) {
        log.error("Handling ConstraintViolationException", ex);
        int status = HttpStatus.BAD_REQUEST.value();
        ErrorResponse errorResponse = new ErrorResponse(status, "Not valid due to validation error: " + ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handle(IllegalArgumentException ex) {
        log.error("Handling IllegalArgumentException", ex);
        int status = HttpStatus.BAD_REQUEST.value();
        ErrorResponse errorResponse = new ErrorResponse(status, ex.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(EntityNotFoundException ex) {
        log.error("Handling EntityNotFoundException", ex);
        int status = HttpStatus.NOT_FOUND.value();
        ErrorResponse errorResponse = new ErrorResponse(status, ex.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        log.error("Handling Exception", ex);
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ErrorResponse errorResponse = new ErrorResponse(status, ex.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
