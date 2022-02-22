package com.example.demo.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler({ProductNotFoundException.class, CategoryNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFound(RuntimeException e) {
        String message = "Entity not found: " + e.getMessage();
        log.error(message);
        Response response = new Response(message);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleUnhandledExceptions(Throwable t) {
        String exceptionUuid = UUID.randomUUID().toString();
        String message = String.format("Внутренняя ошибка сервера. UUID={%s}, body={%s}", exceptionUuid, t.getMessage());
        log.error(message);
        t.printStackTrace();
        Response response = new Response(message);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
