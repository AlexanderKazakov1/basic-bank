package com.kazakov.basic.bank.handler;

import com.kazakov.basic.bank.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResponseExceptionHandlerTest {

    private final ResponseExceptionHandler handler = new ResponseExceptionHandler();

    @Test
    void handleConstraintViolationException() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getMessage()).thenReturn("Validation Error");

        ResponseEntity<ErrorResponse> response = handler.handleConstraintViolationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Not valid due to validation error: Validation Error", response.getBody().message());
    }

    @Test
    void handleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Illegal Argument");

        ResponseEntity<ErrorResponse> response = handler.handle(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Illegal Argument", response.getBody().message());
    }

    @Test
    void handleEntityNotFoundException() {
        EntityNotFoundException ex = new EntityNotFoundException("Entity not found");

        ResponseEntity<ErrorResponse> response = handler.handle(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Entity not found", response.getBody().message());
    }

    @Test
    void handleGenericException() {
        Exception ex = new Exception("Generic Exception");

        ResponseEntity<ErrorResponse> response = handler.handle(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Generic Exception", response.getBody().message());
    }

}