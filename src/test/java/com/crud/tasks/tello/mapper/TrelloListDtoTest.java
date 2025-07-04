package com.crud.tasks.tello.mapper;


import com.crud.tasks.domain.TrelloListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrelloListDtoTest {

    @Test
    void shouldCreateTrelloListDtoAndAccessFields() {
        // Given
        TrelloListDto list = new TrelloListDto("123", "To do", false);

        // Then
        assertEquals("123", list.getId());
        assertEquals("To do", list.getName());
        assertFalse(list.isClosed());
    }


    @Test
    void shouldSerializeToJson() throws Exception {
        // Given
        TrelloListDto list = new TrelloListDto("777", "In progress", false);
        ObjectMapper mapper = new ObjectMapper();

        // When
        String json = mapper.writeValueAsString(list);

        // Then
        assertTrue(json.contains("\"id\":\"777\""));
        assertTrue(json.contains("\"name\":\"In progress\""));
        assertTrue(json.contains("\"closed\":false"));
    }
}

