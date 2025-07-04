package com.crud.tasks.service;

import com.crud.tasks.configuration.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Captor
    private ArgumentCaptor<Mail> mailCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFetchTrelloBoards() {
        // Given
        List<TrelloBoardDto> mockBoards = List.of(new TrelloBoardDto("1", "Test Board", Collections.emptyList()));
        when(trelloClient.getTrelloBoards()).thenReturn(mockBoards);

        // When
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();

        // Then
        assertEquals(1, result.size());
        assertEquals("Test Board", result.get(0).getName());
        verify(trelloClient, times(1)).getTrelloBoards();
    }

    @Test
    void shouldCreateTrelloCardAndSendEmail() {
        // Given
        TrelloCardDto cardDto = new TrelloCardDto("Test Card", "Description", "pos", "123");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("1", "Test Card", "http://test.com", null);


        when(trelloClient.createSingleCard(cardDto)).thenReturn(createdCard);
        when(adminConfig.getAdminMail()).thenReturn("admin@test.com");

        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(cardDto);

        // Then
        assertNotNull(result);
        assertEquals("1", result.getId());

        verify(emailService, times(1)).send(mailCaptor.capture());

        Mail capturedMail = mailCaptor.getValue();
        assertEquals("admin@test.com", capturedMail.getMailTo());
        assertEquals("Tasks: New Trello card", capturedMail.getSubject());
        assertTrue(capturedMail.getMessage().contains("New card: Test Card"));
    }

    @Test
    void shouldThrowExceptionWhenListIdIsNull() {
        // Given
        TrelloCardDto invalidCard = new TrelloCardDto("Card with null list", "desc", "top", null);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> trelloService.createTrelloCard(invalidCard)
        );
        assertEquals("The list ID (idList) is required and cannot be null or empty.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenListIdIsBlank() {
        // Given
        TrelloCardDto invalidCard = new TrelloCardDto("Card with blank list", "desc", "top", "");

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> trelloService.createTrelloCard(invalidCard)
        );
        assertEquals("The list ID (idList) is required and cannot be null or empty.", exception.getMessage());
    }
}
