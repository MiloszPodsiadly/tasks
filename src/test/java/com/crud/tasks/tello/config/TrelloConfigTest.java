package com.crud.tasks.tello.config;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class TrelloConfigTest {

    @Autowired
    private TrelloConfig trelloConfig;

    @Test
    void testTrelloConfigLoadsProperly() {
        assertNotNull(trelloConfig.getTrelloApiEndpoint(), "API Endpoint");
        assertNotNull(trelloConfig.getTrelloAppKey(), "App Key");
        assertNotNull(trelloConfig.getTrelloToken(), "Token");
    }
}
