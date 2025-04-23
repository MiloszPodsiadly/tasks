package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import java.util.ArrayList;
import java.util.List;

import com.crud.tasks.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;
import com.crud.tasks.service.DbService;
import com.crud.tasks.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import com.crud.tasks.domain.Task;
import java.util.Optional;




@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
    private final DbService service;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskController(DbService service, TaskMapper taskMapper) {
        this.service = service;
        this.taskMapper = taskMapper;
    }
    @GetMapping
    public List<TaskDto> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }
    @GetMapping("/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) {
        Task task = service.getTaskById(taskId)
                .orElseThrow(() -> new com.crud.tasks.exception.TaskNotFoundException("Error:" + taskId));
        return taskMapper.mapToTaskDto(task);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        System.out.println("Task deleted with ID: " + taskId);
    }
    @PutMapping
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }
    @PostMapping("/{taskId}")
    public void createTask(@PathVariable Long taskId, @RequestBody TaskDto taskDto) {
        System.out.println("Task created: " + taskDto.getTitle());
    }

}