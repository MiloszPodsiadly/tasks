package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DbService dbService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskRepository taskRepository;

    @TestConfiguration
    static class Config {
        @Bean
        public TaskRepository taskRepository() {
            return Mockito.mock(TaskRepository.class);
        }

        @Bean
        public DbService dbService(TaskRepository taskRepository) {
            return new DbService(taskRepository);
        }

        @Bean
        public TaskMapper taskMapper() {
            return new TaskMapper();
        }
    }

    @Test
    void shouldFetchAllTasks() throws Exception {
        // Given
        Task task = new Task(1L, "Test", "Content");
        when(dbService.getAllTasks()).thenReturn(List.of(task));

        // When & Then
        mockMvc.perform(get("/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test")))
                .andExpect(jsonPath("$[0].content", is("Content")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        // Given
        TaskDto dto = new TaskDto(1L, "New Task", "New Content");
        Gson gson = new Gson();
        String json = gson.toJson(dto);

        // When & Then
        mockMvc.perform(post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        // Given
        TaskDto dto = new TaskDto(1L, "Updated", "Content");
        Task mapped = new Task(1L, "Updated", "Content");

        when(dbService.saveTask(any(Task.class))).thenReturn(mapped);

        Gson gson = new Gson();
        String json = gson.toJson(dto);

        // When & Then
        mockMvc.perform(put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Updated")))
                .andExpect(jsonPath("$.content", is("Content")));
    }

    @Test
    void shouldFetchSingleTask() throws Exception {
        // Given
        Task task = new Task(1L, "Single", "Content");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // When & Then
        mockMvc.perform(get("/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Single")))
                .andExpect(jsonPath("$.content", is("Content")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        // Given
        Task task = new Task(1L, "To Delete", "Content");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // When & Then
        mockMvc.perform(delete("/v1/tasks/1"))
                .andExpect(status().isOk());
    }
}
