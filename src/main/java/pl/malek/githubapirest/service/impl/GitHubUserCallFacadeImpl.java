package pl.malek.githubapirest.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.malek.githubapirest.dto.GitHubUserDTO;
import pl.malek.githubapirest.service.ApiExternalConnectorService;
import pl.malek.githubapirest.service.GitHubUserCallFacade;
import pl.malek.githubapirest.service.GitHubUserCallService;


@Component
public class GitHubUserCallFacadeImpl implements GitHubUserCallFacade {

    private GitHubUserCallService gitHubUserCallService;

    private ApiExternalConnectorService apiExternalConnectorService;

    public GitHubUserCallFacadeImpl(GitHubUserCallService gitHubUserCallService,
                                    @Qualifier("GitHubConnector") ApiExternalConnectorService apiExternalConnectorService) {
        this.gitHubUserCallService = gitHubUserCallService;
        this.apiExternalConnectorService = apiExternalConnectorService;
    }

    @Override
    public GitHubUserDTO getAndUpdateUserCall(String username) {
        GitHubUserDTO gitHubUserDTO = apiExternalConnectorService.getUserDetailsByUsername(username);
        gitHubUserCallService.tryUpdateCallsNumber(username);
        return gitHubUserDTO;
    }

}
