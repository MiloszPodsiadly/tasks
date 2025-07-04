package com.crud.tasks.additionaly;

import com.crud.tasks.configuration.AdminConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = "admin.mail=test@admin.com")
class AdminConfigTest {

    @Autowired
    private AdminConfig adminConfig;

    @Test
    void shouldReturnAdminEmailFromProperties() {
        // When
        String email = adminConfig.getAdminMail();

        // Then
        assertNotNull(email);
        assertEquals("test@admin.com", email);
    }
}

