package pl.malek.githubapirest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.malek.githubapirest.service.impl.GitHubUserCallServiceImpl;
import pl.malek.githubapirest.service.impl.GitHubUserCallUpdateServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static pl.malek.githubapirest.utils.MockDataUtils.getFakeUsername;

@ExtendWith(MockitoExtension.class)
public class GitHubUserCallServiceImplTest {

    @Mock
    private GitHubUserCallUpdateServiceImpl gitHubUserCallUpdateService;

    @InjectMocks
    private GitHubUserCallServiceImpl underTest;

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
