package pl.malek.githubapirest.service;

import pl.malek.githubapirest.dto.GitHubUserWithoutDetailsDTO;

public interface GitHubUserRequestFacade {
    GitHubUserWithoutDetailsDTO getAndUpdateUserRequest(String login);
}
