package pl.malek.githubapirest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.malek.githubapirest.service.impl.GitHubUserRequestServiceImpl;
import pl.malek.githubapirest.service.impl.GitHubUserRequestUpdateServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static pl.malek.githubapirest.utils.MockDataUtils.getFakeUsername;

@ExtendWith(MockitoExtension.class)
public class GitHubUserRequestServiceImplTest {

    @Mock
    private GitHubUserRequestUpdateServiceImpl gitHubUserCallUpdateService;

    @InjectMocks
    private GitHubUserRequestServiceImpl underTest;

    @Captor
    private ArgumentCaptor<String> usernameCaptor;

    @Test
    void shouldStopLookingForNewRecordForUpdateWhenNotThrowingException() {
        final String username = getFakeUsername();

        underTest.tryUpdateCallsNumber(username);

        verify(gitHubUserCallUpdateService).updateCallsNumber(usernameCaptor.capture());

        assertEquals(username, usernameCaptor.getValue());
    }

}
