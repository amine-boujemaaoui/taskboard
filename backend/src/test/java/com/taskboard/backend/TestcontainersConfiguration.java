package com.taskboard.backend;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;

/**
 * Fournit aux tests une base PostgreSQL jetable, démarrée dans un conteneur Docker.
 *
 * <p>{@code @ServiceConnection} indique à Spring Boot de lire l'URL JDBC, l'utilisateur et le
 * mot de passe directement sur le conteneur : ils remplacent ceux d'{@code application.yml}.
 * Les tests qui importent cette configuration n'ont donc pas besoin d'une base lancée à la main.
 */
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer postgresContainer() {
        // Même version majeure que infra/compose.dev.yml.
        return new PostgreSQLContainer("postgres:16");
    }
}
