package pl.malek.githubapirest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import pl.malek.githubapirest.dto.UserDTO;
import pl.malek.githubapirest.exceptions.ExternalApiResponseException;
import pl.malek.githubapirest.service.impl.GitHubConnectorServiceImpl;
import reactor.core.publisher.Flux;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static pl.malek.githubapirest.exceptions.ExceptionMessages.EXTERNAL_API_CONNECTION_ERROR;
import static pl.malek.githubapirest.utils.MockDataUtils.*;

@ExtendWith(MockitoExtension.class)
public class GitHubConnectorServiceImplTests {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClientMock;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    private WebClient.ResponseSpec responseSpecMock;

    @InjectMocks
    private GitHubConnectorServiceImpl underTest;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(underTest, "githubApi", getFakePatternExternalApiUrl());
    }

    @Test
    void shouldThrowExceptionWhenResponseFromExternalApiIsNull() {
        when(webClientBuilder.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(getFakeExternalApiUrl()))
                .thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToFlux(UserDTO.class)).thenReturn(null);

        ExternalApiResponseException exception = assertThrows(ExternalApiResponseException.class,
                () -> underTest.getUserDetailsByUsername(getFakeUsername()));

        assertEquals(EXTERNAL_API_CONNECTION_ERROR.getValue(), exception.getMessage());
    }

    @Test
    void shouldReturnUserDataFromExternalApi() throws MalformedURLException {
        when(webClientBuilder.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(getFakeExternalApiUrl()))
                .thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToFlux(UserDTO.class)).thenReturn(Flux.just(getFakeUserDTO()));

        UserDTO userDTO = underTest.getUserDetailsByUsername(getFakeUsername());

        assertNotNull(userDTO);
        assertEquals(1001, userDTO.getId());
        assertEquals(getFakeUsername(), userDTO.getLogin());
        assertEquals(new URL(getFakePatternExternalApiUrl()), userDTO.getAvatarUrl());
        assertNotNull(userDTO.getCreatedAt());
        assertEquals(getAccountType(), userDTO.getType());
        assertEquals(getFakeName(), userDTO.getName());
    }

    @Test
    void shouldNotCalculateWhenFollowersFromAccountIsNull() throws MalformedURLException {
        when(webClientBuilder.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(getFakeExternalApiUrl()))
                .thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToFlux(UserDTO.class)).thenReturn(Flux.just(getFakeUserDTO()));

        UserDTO userDTO = underTest.getUserDetailsByUsername(getFakeUsername());

        assertNull(userDTO.getFollowers());
        assertNull(userDTO.getCalculations());
    }

    @Test
    void shouldNotCalculateWhenPublicReposFromAccountIsNull() throws MalformedURLException {
        when(webClientBuilder.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(getFakeExternalApiUrl()))
                .thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToFlux(UserDTO.class)).thenReturn(Flux.just(getFakeUserDTO()));

        UserDTO userDTO = underTest.getUserDetailsByUsername(getFakeUsername());

        assertNull(userDTO.getPublicRepos());
        assertNull(userDTO.getCalculations());
    }

    @Test
    void shouldNotCalculateWhenFollowersIsZero() throws MalformedURLException {
        when(webClientBuilder.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(getFakeExternalApiUrl()))
                .thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToFlux(UserDTO.class)).thenReturn(Flux.just(getFakeUserWithFollowersZero()));

        UserDTO userDTO = underTest.getUserDetailsByUsername(getFakeUsername());

        assertNull(userDTO.getPublicRepos());
        assertNull(userDTO.getCalculations());
    }

    @Test
    void shouldCalculateWhenPassAllConditions() throws MalformedURLException {
        when(webClientBuilder.build()).thenReturn(webClientMock);
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(getFakeExternalApiUrl()))
                .thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToFlux(UserDTO.class)).thenReturn(Flux.just(getFakeUserWithFollowersNotZero()));

        UserDTO userDTO = underTest.getUserDetailsByUsername(getFakeUsername());

        assertNotNull(userDTO.getPublicRepos());
        assertNotNull(userDTO.getFollowers());
        assertEquals(0.7636363636363636 ,userDTO.getCalculations());
    }


}
