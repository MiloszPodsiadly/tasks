package com.crud.tasks.exception;
import com.crud.tasks.exception.TaskNotFoundException;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
