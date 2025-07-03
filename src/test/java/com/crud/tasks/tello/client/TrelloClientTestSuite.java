package com.crud.tasks.tello.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.config.TrelloConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrelloClientTestSuite {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        Logger logger = (Logger) LoggerFactory.getLogger(TrelloClient.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        // Mockowanie konfiguracji Trello
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test_key");
        when(trelloConfig.getTrelloToken()).thenReturn("test_token");
    }

    @Test
    void shouldReturnEmptyListAndLogErrorWhenExceptionOccurs() {
        // Given
        doThrow(new RestClientException("Test exception"))
                .when(restTemplate)
                .getForObject(any(URI.class), eq(TrelloBoardDto[].class));

        // When
        List<TrelloBoardDto> result = trelloClient.getTrelloBoards();

        // Then
        assertEquals(Collections.emptyList(), result, "Result should be an empty list due to exception");

        boolean errorLogged = listAppender.list.stream()
                .anyMatch(event -> event.getLevel().toString().equals("ERROR") &&
                        event.getMessage().contains("Error while fetching Trello boards"));

        assertTrue(errorLogged, "Expected ERROR log with the message 'Error while fetching Trello boards' was not found.");
    }

    @Test
    void shouldReturnNullAndLogErrorWhenExceptionOccursWhileCreatingCard() {
        // Given
        TrelloCardDto cardDto = new TrelloCardDto();
        doThrow(new RestClientException("Test exception"))
                .when(restTemplate)
                .postForObject(any(), any(), any());

        // When
        CreatedTrelloCardDto result = trelloClient.createSingleCard(cardDto);

        // Then
        assertNull(result, "Result should be null due to exception");

        boolean errorLogged = listAppender.list.stream()
                .anyMatch(event -> event.getLevel().toString().equals("ERROR") &&
                        event.getMessage().contains("Error while creating Trello card"));

        assertTrue(errorLogged, "Expected ERROR log with the message 'Error while creating Trello card' was not found.");
    }

    @Test
    void shouldFetchTrelloBoards() throws URISyntaxException {
        // Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloUser()).thenReturn("test");

        TrelloBoardDto trelloBoard = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());


        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[]{trelloBoard};

        URI uri = new URI("http://test.com/members/test/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);

        // When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        // Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    void shouldCreateCard() throws URISyntaxException {
        // Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");

        TrelloCardDto trelloCardDto = new TrelloCardDto();
        trelloCardDto.setName("Test task");
        trelloCardDto.setDesc("Test Description");
        trelloCardDto.setPos("top");
        trelloCardDto.setListId("test_id");

        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");

        CreatedTrelloCardDto createdTrelloCard = new CreatedTrelloCardDto();
        createdTrelloCard.setId("1");
        createdTrelloCard.setName("test task");
        createdTrelloCard.setShortUrl("http://test.com");

        when(restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCard);

        // When
        CreatedTrelloCardDto newCard = trelloClient.createSingleCard(trelloCardDto);

        // Then
        assertEquals("1", newCard.getId());
        assertEquals("test task", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    void shouldReturnEmptyList() {
        // Given
        String url = "http://test.com/boards";
        when(restTemplate.getForObject(url, TrelloBoardDto[].class)).thenReturn(null);

        // When
        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);
        List<TrelloBoardDto> result = Arrays.asList(Optional.ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));

        // Then
        assertNotNull(result, "Result should be a non-null list");
        assertEquals(0, result.size(), "List size should be 0 due to null response");
    }
}