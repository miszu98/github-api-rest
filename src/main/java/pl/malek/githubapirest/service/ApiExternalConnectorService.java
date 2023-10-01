package pl.malek.githubapirest.service;

import pl.malek.githubapirest.dto.GitHubUserDTO;

public interface ApiExternalConnectorService {
    GitHubUserDTO getUserDetailsByLogin(String login);
}
