package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.exception.TaskNotFoundException;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllTasks() {
        // Given
        List<Task> taskList = List.of(new Task(1L, "Test Task", "Test Content"));
        when(taskRepository.findAll()).thenReturn(taskList);

        // When
        List<Task> result = dbService.getAllTasks();

        // Then
        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
    }

    @Test
    void shouldSaveTask() {
        // Given
        Task task = new Task(1L, "Save Test", "Content");
        when(taskRepository.save(task)).thenReturn(task);

        // When
        Task savedTask = dbService.saveTask(task);

        // Then
        assertNotNull(savedTask);
        assertEquals("Save Test", savedTask.getTitle());
    }

    @Test
    void shouldGetTaskById() throws TaskNotFoundException {
        // Given
        Task task = new Task(1L, "Get Test", "Content");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // When
        Task result = dbService.getTask(1L);

        // Then
        assertNotNull(result);
        assertEquals("Get Test", result.getTitle());
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        // Given
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(TaskNotFoundException.class, () -> dbService.getTask(1L));
    }

    @Test
    void shouldDeleteTaskById() throws TaskNotFoundException {
        // Given
        Task task = new Task(1L, "Delete Test", "Content");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        // When
        Task deletedTask = dbService.deleteById(1L);

        // Then
        assertEquals("Delete Test", deletedTask.getTitle());
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonexistentTask() {
        // Given
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(TaskNotFoundException.class, () -> dbService.deleteById(1L));
    }
}
