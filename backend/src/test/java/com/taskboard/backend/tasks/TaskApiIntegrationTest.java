package com.taskboard.backend.tasks;

import static org.assertj.core.api.Assertions.assertThat;

import com.taskboard.backend.TestcontainersConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

/**
 * Teste l'API des tâches de bout en bout : requête HTTP, validation, JPA, Flyway et une vraie
 * base PostgreSQL (fournie par Testcontainers).
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class TaskApiIntegrationTest {

    @Autowired
    private MockMvcTester mvc;

    @Autowired
    private TaskRepository repository;

    @BeforeEach
    void cleanDatabase() {
        // La base est partagée par tous les tests : chaque test part d'une table vide.
        repository.deleteAll();
    }

    @Test
    @DisplayName("POST /api/tasks crée une tâche, puis GET /api/tasks la renvoie")
    void createThenList() {
        String body = """
                {
                  "title": "Écrire les tests",
                  "description": "Avec Testcontainers",
                  "status": "TODO",
                  "priority": "HIGH"
                }
                """;

        assertThat(mvc.post().uri("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(body))
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .extractingPath("$.id").isNotNull();

        assertThat(mvc.get().uri("/api/tasks"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[0].title").isEqualTo("Écrire les tests");

        assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("POST /api/tasks refuse un titre vide (400) et n'enregistre rien")
    void rejectsBlankTitle() {
        String body = """
                {
                  "title": "  ",
                  "status": "TODO",
                  "priority": "LOW"
                }
                """;

        assertThat(mvc.post().uri("/api/tasks").contentType(MediaType.APPLICATION_JSON).content(body))
                .hasStatus(HttpStatus.BAD_REQUEST);

        assertThat(repository.count()).isZero();
    }
}
