package pl.malek.githubapirest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.malek.githubapirest.entity.GitHubUserCallsEntity;
import pl.malek.githubapirest.repository.GitHubUserCallRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubUserCallUpdateServiceImpl {

    private final GitHubUserCallRepository gitHubUserCallRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateCallsNumber(String username) {
        Optional<GitHubUserCallsEntity> optionalGitHubUserCalls = gitHubUserCallRepository.findByUsername(username);
        boolean userNotExists = optionalGitHubUserCalls.isEmpty();

        if (userNotExists) {
            GitHubUserCallsEntity newUserRegister = createNewUserRegister(username);
            gitHubUserCallRepository.save(newUserRegister);
        } else {
            GitHubUserCallsEntity gitHubUserCallsEntity = optionalGitHubUserCalls.get();
            incrementCallsNumber(gitHubUserCallsEntity);
            gitHubUserCallRepository.save(gitHubUserCallsEntity);
        }
        throw new RuntimeException("dsadaa");
    }

    private void incrementCallsNumber(GitHubUserCallsEntity gitHubUserCallsEntity) {
        Long callsNumber = gitHubUserCallsEntity.getCallsNumber();
        gitHubUserCallsEntity.setCallsNumber(callsNumber + 1);
    }

    private GitHubUserCallsEntity createNewUserRegister(String username) {
        return GitHubUserCallsEntity.builder()
                .username(username)
                .callsNumber(1L)
                .build();
    }


}
