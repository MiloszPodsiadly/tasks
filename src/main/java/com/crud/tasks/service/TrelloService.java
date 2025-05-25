package com.crud.tasks.service;

import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.crud.tasks.domain.TrelloBoardDto;
import java.util.List;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.configuration.AdminConfig;
import java.util.*;
@Service
@RequiredArgsConstructor
public class TrelloService {
    private final TrelloClient trelloClient;
    private final SimpleEmailService emailService;
    private final AdminConfig adminConfig;
    private static final String SUBJECT = "Tasks: Once a day email";
    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }
    public CreatedTrelloCardDto createTrelloCard(final TrelloCardDto trelloCardDto) {
        if (trelloCardDto.getListId() == null || trelloCardDto.getListId().isBlank()) {
            throw new IllegalArgumentException("The list ID (idList) is required and cannot be null or empty.");
        }

        CreatedTrelloCardDto newCard = trelloClient.createSingleCard(trelloCardDto);

        Optional.ofNullable(newCard).ifPresent(card ->
                emailService.send(
                        Mail.builder()
                                .mailTo(adminConfig.getAdminMail())
                                .subject("Tasks: New Trello card")
                                .message("New card: " + trelloCardDto.getName() + " has been created on your Trello account")
                                .build()
                )
        );

        return newCard;
    }

}