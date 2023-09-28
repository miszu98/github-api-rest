package pl.malek.githubapirest.service;

import pl.malek.githubapirest.dto.GitHubUserDTO;

public interface GitHubUserCallFacade {
    GitHubUserDTO getAndUpdateUserCall(String username);
}
