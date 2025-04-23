package com.crud.tasks.controller;

import com.crud.tasks.exception.TaskNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {
    public ResponseEntity<Object> handleTaskNotFoundException(TaskNotFoundException exception) {
        return new ResponseEntity<>("Task with given id doesn't exist", HttpStatus.BAD_REQUEST);
    }
}
