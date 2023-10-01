package pl.malek.githubapirest.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import pl.malek.githubapirest.configuration.AbstractIntegrationTestConfiguration;
import pl.malek.githubapirest.dto.GitHubUserDTO;
import pl.malek.githubapirest.entity.GitHubUserRequestEntity;
import pl.malek.githubapirest.exceptions.ExceptionMessages;
import pl.malek.githubapirest.repository.GitHubUserRequestRepository;
import pl.malek.githubapirest.service.impl.GitHubConnectorServiceImpl;

import java.net.URL;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.malek.githubapirest.utils.MockDataUtils.*;

public class GitHubUserControllerTest extends AbstractIntegrationTestConfiguration {
    private static final String API_URL = "/api/v1/users/";

    @Autowired
    private GitHubUserRequestRepository gitHubUserRequestRepository;

    @MockBean
    private GitHubConnectorServiceImpl gitHubConnectorService;

    @BeforeEach
    void setUp() {
        GitHubUserRequestEntity gitHubUserRequestEntity = getFakeGitHubUserCallsEntity();
        gitHubUserRequestRepository.save(gitHubUserRequestEntity);
    }

    @AfterEach
    void tearDown() {
        gitHubUserRequestRepository.deleteAll();
    }

    @Test
    void shouldUpdateAlreadyExistedRecordInDatabase() throws Exception {
        final String existedUsernameInDatabase = getFakeUsername();
        final GitHubUserDTO gitHubUserDTO = getFakeUserDTO();

        when(gitHubConnectorService.getUserDetailsByLogin(existedUsernameInDatabase))
                .thenReturn(gitHubUserDTO);

        mockMvc.perform(get("/api/v1/users/" + existedUsernameInDatabase)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value("1001"),
                        jsonPath("$.login").value("mock_test_username"),
                        jsonPath("$.name").value("Robert C. Martin"),
                        jsonPath("$.type").value("user"),
                        jsonPath("$.calculations").isEmpty(),
                        jsonPath("$.avatarUrl").value(new URL(getFakeExternalApiUrl()).toString())
                );

        Optional<GitHubUserRequestEntity> gitHubUserCallsEntityOptional = gitHubUserRequestRepository
                .findByLogin(existedUsernameInDatabase);
        GitHubUserRequestEntity gitHubUserRequestEntity = gitHubUserCallsEntityOptional.get();

        assertEquals(11, gitHubUserRequestEntity.getRequestCount());
        assertEquals(5, gitHubUserRequestEntity.getVersion());
    }

    @Test
    void shouldCreateFirstUserCallRecordInDatabase() throws Exception {
        final String notExistedUsernameInDatabase = getFakeNotExistingUsername();
        final GitHubUserDTO gitHubUserDTO = getFakeUserDTO();

        when(gitHubConnectorService.getUserDetailsByLogin(notExistedUsernameInDatabase))
                .thenReturn(gitHubUserDTO);

        mockMvc.perform(get("/api/v1/users/" + notExistedUsernameInDatabase)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value("1001"),
                        jsonPath("$.login").value("mock_test_username"),
                        jsonPath("$.name").value("Robert C. Martin"),
                        jsonPath("$.type").value("user"),
                        jsonPath("$.calculations").isEmpty(),
                        jsonPath("$.avatarUrl").value(new URL(getFakeExternalApiUrl()).toString())
                );

        Optional<GitHubUserRequestEntity> gitHubUserCallsEntityOptional = gitHubUserRequestRepository
                .findByLogin(notExistedUsernameInDatabase);
        GitHubUserRequestEntity gitHubUserRequestEntity = gitHubUserCallsEntityOptional.get();

        assertEquals(1, gitHubUserRequestEntity.getRequestCount());
        assertEquals(0, gitHubUserRequestEntity.getVersion());
    }

    @Test
    void shouldThrowExceptionWhenUsernameIsEmptyOrBlank() throws Exception {
        final String blankUsername = " ";

        mockMvc.perform(get("/api/v1/users/" + blankUsername)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.errorMessage").value(ExceptionMessages.LOGIN_CANNOT_BE_EMPTY_OR_BLANK.getValue()),
                        jsonPath("$.errorStatus").value("409"),
                        jsonPath("$.errorTime").isNotEmpty()
                );
    }

    @Test
    void shouldCreateNewUserCallAndHandleLostUpdateProblem() throws Exception {
        final String notExistedUsernameInDatabase = getFakeNotExistingUsername();
        final GitHubUserDTO gitHubUserDTO = getFakeUserDTO();

        when(gitHubConnectorService.getUserDetailsByLogin(notExistedUsernameInDatabase))
                .thenReturn(gitHubUserDTO);

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 4; i++) {
            executorService.execute(() -> {
                try {
                    sendAsyncRequestForGetDataAboutUser(notExistedUsernameInDatabase);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        Optional<GitHubUserRequestEntity> gitHubUserCallsEntityOptional = gitHubUserRequestRepository
                .findByLogin(notExistedUsernameInDatabase);
        GitHubUserRequestEntity gitHubUserRequestEntity = gitHubUserCallsEntityOptional.get();

        assertEquals(4, gitHubUserRequestEntity.getRequestCount());
        assertEquals(3, gitHubUserRequestEntity.getVersion());
    }

    @Test
    void shouldUpdateUserCallAndHandleLostUpdateProblem() throws Exception {
        final String existedUsernameInDatabase = getFakeUsername();
        final GitHubUserDTO gitHubUserDTO = getFakeUserDTO();

        when(gitHubConnectorService.getUserDetailsByLogin(existedUsernameInDatabase))
                .thenReturn(gitHubUserDTO);

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                try {
                    sendAsyncRequestForGetDataAboutUser(existedUsernameInDatabase);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        Optional<GitHubUserRequestEntity> gitHubUserCallsEntityOptional = gitHubUserRequestRepository
                .findByLogin(existedUsernameInDatabase);
        GitHubUserRequestEntity gitHubUserRequestEntity = gitHubUserCallsEntityOptional.get();

        assertEquals(20, gitHubUserRequestEntity.getRequestCount());
        assertEquals(14, gitHubUserRequestEntity.getVersion());
    }

    private void sendAsyncRequestForGetDataAboutUser(String username) throws Exception {
        mockMvc.perform(get(API_URL + username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value("1001"),
                        jsonPath("$.login").value("mock_test_username"),
                        jsonPath("$.name").value("Robert C. Martin"),
                        jsonPath("$.type").value("user"),
                        jsonPath("$.calculations").isEmpty(),
                        jsonPath("$.avatarUrl").value(new URL(getFakeExternalApiUrl()).toString())
                );
    }

}
