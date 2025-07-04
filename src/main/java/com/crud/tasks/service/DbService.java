package com.crud.tasks.service;

import com.crud.tasks.repository.TaskRepository;
import lombok.Setter;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.crud.tasks.domain.Task;
import java.util.List;

import com.crud.tasks.exception.TaskNotFoundException;

@Service
@RequiredArgsConstructor
public class DbService {
    private final TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task saveTask(final Task task) {
        return repository.save(task);
    }

    public Task getTask(Long taskId) throws TaskNotFoundException {
        return repository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    }

    public Task deleteById(Long taskId) throws TaskNotFoundException {
        return repository.findById(taskId)
                .map(task -> {
                    repository.delete(task);
                    return task;
                })
                .orElseThrow(TaskNotFoundException::new);
    }
}
