package pl.malek.githubapirest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import pl.malek.githubapirest.service.GitHubUserCallService;


@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubUserCallServiceImpl implements GitHubUserCallService {

    private final GitHubUserCallUpdateServiceImpl gitHubUserCallUpdateService;

    @Override
    public void tryUpdateCallsNumber(String username) {
        boolean searchNotUpdatedRecord = true;
        while (searchNotUpdatedRecord) {
            try {
                gitHubUserCallUpdateService.updateCallsNumber(username);
                searchNotUpdatedRecord = false;
            } catch (ObjectOptimisticLockingFailureException | DataIntegrityViolationException e) {
                log.warn(e.getMessage());
            }
        }
    }

}
