package com.crud.tasks.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskDtoTest {

    @Test
    void shouldCreateTaskDtoWithNoArgsConstructor() {
        // given
        TaskDto taskDto = new TaskDto();

        // when
        taskDto.setId(1L);
        taskDto.setTitle("Test Task");
        taskDto.setContent("Test Content");

        // then
        assertEquals(1L, taskDto.getId());
        assertEquals("Test Task", taskDto.getTitle());
        assertEquals("Test Content", taskDto.getContent());
    }

    @Test
    void shouldCreateTaskDtoWithAllArgsConstructor() {
        // given
        TaskDto taskDto = new TaskDto(1L, "Test Task", "Test Content");

        // then
        assertEquals(1L, taskDto.getId());
        assertEquals("Test Task", taskDto.getTitle());
        assertEquals("Test Content", taskDto.getContent());
    }

    @Test
    void testEqualsAndHashCode() {
        // given
        TaskDto task1 = new TaskDto(1L, "Task Title", "Task Content");
        TaskDto task2 = new TaskDto(1L, "Task Title", "Task Content");
        TaskDto task3 = new TaskDto(2L, "Other Title", "Other Content");

        // when & then
        assertEquals(task1, task2);
        assertEquals(task1.hashCode(), task2.hashCode());
        assertNotEquals(task1, task3);
    }
}