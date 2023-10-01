package pl.malek.githubapirest.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.malek.githubapirest.dto.GitHubUserDTO;
import pl.malek.githubapirest.dto.GitHubUserWithoutDetailsDTO;
import pl.malek.githubapirest.service.ApiExternalConnectorService;
import pl.malek.githubapirest.service.GitHubUserRequestFacade;
import pl.malek.githubapirest.service.GitHubUserRequestService;
import pl.malek.githubapirest.validators.processors.LoginValidationProcessor;


@Component
public class GitHubUserRequestFacadeImpl implements GitHubUserRequestFacade {

    private GitHubUserRequestService gitHubUserRequestService;

    private ApiExternalConnectorService apiExternalConnectorService;

    private LoginValidationProcessor loginValidationProcessor;

    public GitHubUserRequestFacadeImpl(GitHubUserRequestService gitHubUserRequestService,
                                       @Qualifier("GitHubConnector") ApiExternalConnectorService apiExternalConnectorService,
                                       LoginValidationProcessor loginValidationProcessor) {
        this.gitHubUserRequestService = gitHubUserRequestService;
        this.apiExternalConnectorService = apiExternalConnectorService;
        this.loginValidationProcessor = loginValidationProcessor;
    }

    @Override
    @Transactional
    public GitHubUserWithoutDetailsDTO getAndUpdateUserRequest(String login) {
        loginValidationProcessor.validate(login);
        GitHubUserDTO gitHubUserDTO = apiExternalConnectorService.getUserDetailsByLogin(login);
        gitHubUserRequestService.tryUpdateCallsNumber(login);
        return GitHubUserWithoutDetailsDTO.map(gitHubUserDTO);
    }

}
