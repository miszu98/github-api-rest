package pl.malek.githubapirest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.malek.githubapirest.entity.GitHubUserRequestEntity;
import pl.malek.githubapirest.repository.GitHubUserRequestRepository;
import pl.malek.githubapirest.service.impl.GitHubUserRequestUpdateServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.malek.githubapirest.utils.MockDataUtils.getFakeGitHubUserCallsEntity;
import static pl.malek.githubapirest.utils.MockDataUtils.getFakeUsername;

@ExtendWith(MockitoExtension.class)
public class GitHubUserRequestUpdateServiceImplTest {

    @Mock
    private GitHubUserRequestRepository gitHubUserRequestRepository;

    @InjectMocks
    private GitHubUserRequestUpdateServiceImpl underTest;

    @Captor
    private ArgumentCaptor<GitHubUserRequestEntity> callsEntityArgumentCaptor;

    @Test
    void shouldCreateBrandNewRecordIfNotFound() {
        final String username = getFakeUsername();

        when(gitHubUserRequestRepository.findByLogin(username)).thenReturn(Optional.empty());

        underTest.updateCallsNumber(username);

        verify(gitHubUserRequestRepository).save(callsEntityArgumentCaptor.capture());

        GitHubUserRequestEntity capturedArgument = callsEntityArgumentCaptor.getValue();

        assertEquals(username, capturedArgument.getLogin());
        assertEquals(1L, capturedArgument.getRequestCount().longValue());
    }

    @Test
    void shouldUpdateCallNumberIfUserExists() {
        final String username = getFakeUsername();
        final GitHubUserRequestEntity gitHubUserRequestEntity = getFakeGitHubUserCallsEntity();

        when(gitHubUserRequestRepository.findByLogin(username)).thenReturn(Optional.of(gitHubUserRequestEntity));

        underTest.updateCallsNumber(username);

        verify(gitHubUserRequestRepository).save(callsEntityArgumentCaptor.capture());

        GitHubUserRequestEntity capturedArgument = callsEntityArgumentCaptor.getValue();

        assertEquals(11L, capturedArgument.getRequestCount().longValue());
        assertEquals(username, capturedArgument.getLogin());
    }
}
