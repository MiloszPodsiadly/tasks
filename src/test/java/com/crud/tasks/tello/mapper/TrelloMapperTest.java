package com.crud.tasks.tello.mapper;


import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrelloMapperTest {

    private TrelloMapper trelloMapper;

    @BeforeEach
    void setUp() {
        trelloMapper = new TrelloMapper();
    }

    @Test
    void shouldMapToBoards() {
        // given
        TrelloListDto listDto = new TrelloListDto("1", "List DTO", false);
        TrelloBoardDto boardDto = new TrelloBoardDto("10", "Board DTO", List.of(listDto));

        // when
        List<TrelloBoard> result = trelloMapper.mapToBoards(List.of(boardDto));

        // then
        assertEquals(1, result.size());
        TrelloBoard board = result.get(0);
        assertEquals("10", board.getId());
        assertEquals("Board DTO", board.getName());
        assertEquals(1, board.getLists().size());
        assertEquals("1", board.getLists().get(0).getId());
    }

    @Test
    void shouldMapToBoardsDto() {
        // given
        TrelloList list = new TrelloList("2", "List", true);
        TrelloBoard board = new TrelloBoard("20", "Board", List.of(list));

        // when
        List<TrelloBoardDto> result = trelloMapper.mapToBoardsDto(List.of(board));

        // then
        assertEquals(1, result.size());
        TrelloBoardDto boardDto = result.get(0);
        assertEquals("20", boardDto.getId());
        assertEquals("Board", boardDto.getName());
        assertEquals(1, boardDto.getLists().size());
        assertEquals("2", boardDto.getLists().get(0).getId());
    }

    @Test
    void shouldMapToList() {
        // given
        TrelloListDto listDto = new TrelloListDto("1", "List DTO", false);

        // when
        List<TrelloList> result = trelloMapper.mapToList(List.of(listDto));

        // then
        assertEquals(1, result.size());
        TrelloList list = result.get(0);
        assertEquals("1", list.getId());
        assertEquals("List DTO", list.getName());
        assertFalse(list.isClosed());
    }

    @Test
    void shouldMapToListDto() {
        // given
        TrelloList list = new TrelloList("2", "List", true);

        // when
        List<TrelloListDto> result = trelloMapper.mapToListDto(List.of(list));

        // then
        assertEquals(1, result.size());
        TrelloListDto dto = result.get(0);
        assertEquals("2", dto.getId());
        assertEquals("List", dto.getName());
        assertTrue(dto.isClosed());
    }

    @Test
    void shouldMapToCardDto() {
        // given
        TrelloCard card = new TrelloCard("Card", "desc", "top", "100");

        // when
        TrelloCardDto dto = trelloMapper.mapToCardDto(card);

        // then
        assertEquals("Card", dto.getName());
        assertEquals("desc", dto.getDesc());
        assertEquals("top", dto.getPos());
        assertEquals("100", dto.getListId());
    }

    @Test
    void shouldMapToCard() {
        // given
        TrelloCardDto dto = new TrelloCardDto("Card DTO", "desc dto", "bottom", "200");

        // when
        TrelloCard card = trelloMapper.mapToCard(dto);

        // then
        assertEquals("Card DTO", card.getName());
        assertEquals("desc dto", card.getDesc());
        assertEquals("bottom", card.getPos());
        assertEquals("200", card.getListId());
    }
}

