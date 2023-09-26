package pl.malek.githubapirest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.malek.githubapirest.service.GitHubUserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubUserServiceImpl implements GitHubUserService {

    @Override
    public void updateNumberOfCalls(String username) {

    }

}
