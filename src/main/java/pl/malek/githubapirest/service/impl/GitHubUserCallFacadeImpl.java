package pl.malek.githubapirest.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.malek.githubapirest.dto.GitHubUserDTO;
import pl.malek.githubapirest.dto.GitHubUserWithoutDetailsDTO;
import pl.malek.githubapirest.service.ApiExternalConnectorService;
import pl.malek.githubapirest.service.GitHubUserCallFacade;
import pl.malek.githubapirest.service.GitHubUserCallService;
import pl.malek.githubapirest.validators.processors.UsernameValidationProcessor;


@Component
public class GitHubUserCallFacadeImpl implements GitHubUserCallFacade {

    private GitHubUserCallService gitHubUserCallService;

    private ApiExternalConnectorService apiExternalConnectorService;

    private UsernameValidationProcessor usernameValidationProcessor;

    public GitHubUserCallFacadeImpl(GitHubUserCallService gitHubUserCallService,
                                    @Qualifier("GitHubConnector") ApiExternalConnectorService apiExternalConnectorService,
                                    UsernameValidationProcessor usernameValidationProcessor) {
        this.gitHubUserCallService = gitHubUserCallService;
        this.apiExternalConnectorService = apiExternalConnectorService;
        this.usernameValidationProcessor = usernameValidationProcessor;
    }

    @Override
    @Transactional
    public GitHubUserWithoutDetailsDTO getAndUpdateUserCall(String username) {
        usernameValidationProcessor.validate(username);
        GitHubUserDTO gitHubUserDTO = apiExternalConnectorService.getUserDetailsByUsername(username);
        gitHubUserCallService.tryUpdateCallsNumber(username);
        return GitHubUserWithoutDetailsDTO.map(gitHubUserDTO);
    }

}
