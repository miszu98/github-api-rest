package pl.malek.githubapirest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import pl.malek.githubapirest.entity.GitHubUserCallsEntity;
import pl.malek.githubapirest.repository.GitHubUserCallRepository;
import pl.malek.githubapirest.service.GitHubUserCallService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubUserCallServiceImpl implements GitHubUserCallService {

    private final GitHubUserCallRepository gitHubUserCallRepository;

    private void updateCallsNumber(String username) {
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
    }

    @Override
    public void tryUpdateCallsNumber(String username) {
        boolean searchNotUpdatedRecord = true;
        while (searchNotUpdatedRecord) {
            try {
                updateCallsNumber(username);
                searchNotUpdatedRecord = false;
            } catch (ObjectOptimisticLockingFailureException | DataIntegrityViolationException e) {
                log.warn(e.getMessage());
            }
        }
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
