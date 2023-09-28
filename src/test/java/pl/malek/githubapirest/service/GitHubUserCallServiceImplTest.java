package pl.malek.githubapirest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.malek.githubapirest.entity.GitHubUserCallsEntity;
import pl.malek.githubapirest.repository.GitHubUserCallRepository;
import pl.malek.githubapirest.service.impl.GitHubUserCallServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.malek.githubapirest.utils.MockDataUtils.getFakeGitHubUserCallsEntity;
import static pl.malek.githubapirest.utils.MockDataUtils.getFakeUsername;

@ExtendWith(MockitoExtension.class)
public class GitHubUserCallServiceImplTest {

    @Mock
    private GitHubUserCallRepository gitHubUserCallRepository;

    @InjectMocks
    private GitHubUserCallServiceImpl underTest;

    @Captor
    private ArgumentCaptor<GitHubUserCallsEntity> callsEntityArgumentCaptor;

    @Test
    void shouldCreateBrandNewRecordIfNotFound() {
        final String username = getFakeUsername();

        when(gitHubUserCallRepository.findByUsername(username)).thenReturn(Optional.empty());

        underTest.tryUpdateCallsNumber(username);

        verify(gitHubUserCallRepository).save(callsEntityArgumentCaptor.capture());

        GitHubUserCallsEntity capturedArgument = callsEntityArgumentCaptor.getValue();

        assertEquals(username, capturedArgument.getUsername());
        assertEquals(1L, capturedArgument.getCallsNumber().longValue());
    }

    @Test
    void shouldUpdateCallNumberIfUserExists() {
        final String username = getFakeUsername();
        final GitHubUserCallsEntity gitHubUserCallsEntity = getFakeGitHubUserCallsEntity();

        when(gitHubUserCallRepository.findByUsername(username)).thenReturn(Optional.of(gitHubUserCallsEntity));

        underTest.tryUpdateCallsNumber(username);

        verify(gitHubUserCallRepository).save(callsEntityArgumentCaptor.capture());

        GitHubUserCallsEntity capturedArgument = callsEntityArgumentCaptor.getValue();

        assertEquals(11L, capturedArgument.getCallsNumber().longValue());
        assertEquals(username, capturedArgument.getUsername());
    }

}
