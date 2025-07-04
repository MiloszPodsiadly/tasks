package com.crud.tasks.tello.mapper;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.CreatedTrelloCardDto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreatedTrelloCardDtoTest {

    @Test
    void shouldCreateCreatedTrelloCardDtoWithNestedFields() {
        // Given
        Trello trello = new Trello();
        trello.setBoard(3);
        trello.setCard(7);

        AttachmentsByType attachments = new AttachmentsByType();
        attachments.setTrello(trello);

        Badges badges = new Badges();
        badges.setVotes(5);
        badges.setAttachmentsByType(attachments);

        CreatedTrelloCardDto card = new CreatedTrelloCardDto("1", "Test Card", "http://url.com", badges);

        // Then
        assertEquals("1", card.getId());
        assertEquals("Test Card", card.getName());
        assertEquals("http://url.com", card.getShortUrl());
        assertNotNull(card.getBadges());
        assertEquals(5, card.getBadges().getVotes());
        assertEquals(3, card.getBadges().getAttachmentsByType().getTrello().getBoard());
        assertEquals(7, card.getBadges().getAttachmentsByType().getTrello().getCard());
    }

    @Test
    void shouldDeserializeFromJson() throws Exception {
        // language=JSON
        String json = """
        {
            "id": "1",
            "name": "New Card",
            "shortUrl": "http://test.com",
            "badges": {
                "votes": 10,
                "attachmentsByType": {
                    "trello": {
                        "board": 1,
                        "card": 2
                    }
                }
            }
        }
        """;

        ObjectMapper mapper = new ObjectMapper();

        // When
        CreatedTrelloCardDto result = mapper.readValue(json, CreatedTrelloCardDto.class);

        // Then
        assertEquals("1", result.getId());
        assertEquals("New Card", result.getName());
        assertEquals("http://test.com", result.getShortUrl());
        assertEquals(10, result.getBadges().getVotes());
        assertEquals(1, result.getBadges().getAttachmentsByType().getTrello().getBoard());
        assertEquals(2, result.getBadges().getAttachmentsByType().getTrello().getCard());
    }

    @Test
    void shouldSerializeToJson() throws Exception {
        // Given
        Trello trello = new Trello();
        trello.setBoard(9);
        trello.setCard(4);

        AttachmentsByType attachments = new AttachmentsByType();
        attachments.setTrello(trello);

        Badges badges = new Badges();
        badges.setVotes(2);
        badges.setAttachmentsByType(attachments);

        CreatedTrelloCardDto card = new CreatedTrelloCardDto("abc123", "Serialize Test", "http://link.com", badges);

        ObjectMapper mapper = new ObjectMapper();

        // When
        String json = mapper.writeValueAsString(card);

        // Then
        assertTrue(json.contains("\"id\":\"abc123\""));
        assertTrue(json.contains("\"name\":\"Serialize Test\""));
        assertTrue(json.contains("\"shortUrl\":\"http://link.com\""));
        assertTrue(json.contains("\"votes\":2"));
        assertTrue(json.contains("\"board\":9"));
        assertTrue(json.contains("\"card\":4"));
    }
}

