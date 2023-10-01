package pl.malek.githubapirest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.malek.githubapirest.entity.GitHubUserRequestEntity;
import pl.malek.githubapirest.repository.GitHubUserRequestRepository;
import pl.malek.githubapirest.service.GitHubUserRequestUpdateService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubUserRequestUpdateServiceImpl implements GitHubUserRequestUpdateService {

    private final GitHubUserRequestRepository gitHubUserRequestRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateCallsNumber(String login) {
        Optional<GitHubUserRequestEntity> optionalGitHubUserRequest = gitHubUserRequestRepository.findByLogin(login);
        boolean userNotExists = optionalGitHubUserRequest.isEmpty();

        if (userNotExists) {
            log.info("User not exist in database, create new one");
            GitHubUserRequestEntity newUserRegister = createNewUserRegister(login);
            gitHubUserRequestRepository.save(newUserRegister);
        } else {
            log.info("Updating existing record for user: {}", login);
            GitHubUserRequestEntity gitHubUserRequestEntity = optionalGitHubUserRequest.get();
            incrementRequestNumber(gitHubUserRequestEntity);
            gitHubUserRequestRepository.save(gitHubUserRequestEntity);
        }
    }

    private void incrementRequestNumber(GitHubUserRequestEntity gitHubUserRequestEntity) {
        Long requestCount = gitHubUserRequestEntity.getRequestCount();
        gitHubUserRequestEntity.setRequestCount(requestCount + 1);
    }

    private GitHubUserRequestEntity createNewUserRegister(String username) {
        return GitHubUserRequestEntity.builder()
                .login(username)
                .requestCount(1L)
                .build();
    }


}
