package pl.malek.githubapirest.service;

import pl.malek.githubapirest.dto.GitHubUserWithoutDetailsDTO;

public interface GitHubUserCallFacade {
    GitHubUserWithoutDetailsDTO getAndUpdateUserCall(String username);
}
