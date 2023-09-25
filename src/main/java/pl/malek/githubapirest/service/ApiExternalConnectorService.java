package pl.malek.githubapirest.service;

import pl.malek.githubapirest.dto.UserDTO;

public interface ApiExternalConnectorService {
    UserDTO getUserDetailsByUsername(String username);
}
