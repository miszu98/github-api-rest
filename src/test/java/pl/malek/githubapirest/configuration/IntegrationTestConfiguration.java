package pl.malek.githubapirest.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.PostgreSQLContainer;

@Configuration
@Profile("integration-testing")
public class IntegrationTestConfiguration {

    private PostgreSQLContainer container;

    public IntegrationTestConfiguration() {
        this.container = new PostgreSQLContainer("postgres:11")
                .withDatabaseName("integrationTestDB")
                .withPassword("123")
                .withUsername("root");
        this.container.start();
    }
}
