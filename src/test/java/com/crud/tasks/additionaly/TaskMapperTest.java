package com.crud.tasks.additionaly;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    void shouldMapToTask() {
        // Given
        TaskDto dto = new TaskDto(1L, "Test title", "Test content");

        // When
        Task task = taskMapper.mapToTask(dto);

        // Then
        assertEquals(1L, task.getId());
        assertEquals("Test title", task.getTitle());
        assertEquals("Test content", task.getContent());
    }

    @Test
    void shouldMapToTaskDto() {
        // Given
        Task task = new Task(2L, "Other title", "Other content");

        // When
        TaskDto dto = taskMapper.mapToTaskDto(task);

        // Then
        assertEquals(2L, dto.getId());
        assertEquals("Other title", dto.getTitle());
        assertEquals("Other content", dto.getContent());
    }

    @Test
    void shouldMapToTaskDtoList() {
        // Given
        List<Task> taskList = List.of(
                new Task(1L, "Title 1", "Content 1"),
                new Task(2L, "Title 2", "Content 2")
        );

        // When
        List<TaskDto> dtoList = taskMapper.mapToTaskDtoList(taskList);

        // Then
        assertEquals(2, dtoList.size());

        assertEquals(1L, dtoList.get(0).getId());
        assertEquals("Title 1", dtoList.get(0).getTitle());

        assertEquals(2L, dtoList.get(1).getId());
        assertEquals("Title 2", dtoList.get(1).getTitle());
    }
}


