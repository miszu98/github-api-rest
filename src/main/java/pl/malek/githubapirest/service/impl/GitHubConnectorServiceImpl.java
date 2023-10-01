package pl.malek.githubapirest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.malek.githubapirest.dto.GitHubUserDTO;
import pl.malek.githubapirest.exceptions.ExternalApiResponseException;
import pl.malek.githubapirest.service.ApiExternalConnectorService;
import reactor.core.publisher.Flux;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.malek.githubapirest.exceptions.ExceptionMessages.EXTERNAL_API_CONNECTION_ERROR;

@Slf4j
@Service("GitHubConnector")
@RequiredArgsConstructor
public class GitHubConnectorServiceImpl implements ApiExternalConnectorService {

    @Value("${github.api}")
    private String githubApi;

    private final WebClient.Builder webClientBuilder;

    @Override
    public GitHubUserDTO getUserDetailsByLogin(String login) {
        log.info("Trying to get information about: {}", login);
        Flux<GitHubUserDTO> userDTOFlux = getDataFromGitHubApi(login);
        validateResponseFromExternalApi(userDTOFlux);

        GitHubUserDTO gitHubUserDTO = userDTOFlux.blockFirst();

        if (nonNull(gitHubUserDTO)) {
            doCalculations(gitHubUserDTO);
        }
        return gitHubUserDTO;
    }

    private void doCalculations(GitHubUserDTO gitHubUserDTO) {
        Integer followers = gitHubUserDTO.getFollowers();
        Integer publicRepos = gitHubUserDTO.getPublicRepos();

        if (followersAndPublicReposAreNotNull(followers, publicRepos)) {
            Double calculateResult = calculate(followers, publicRepos);
            gitHubUserDTO.setCalculations(calculateResult);
        }
    }

    private Double calculate(Integer followers, Integer publicRepos) {
        boolean followersIsNotZero = followers != 0;
        if (followersIsNotZero) {
            return ((double) 6 / followers) * (2 + publicRepos);
        } return null;
    }

    private Flux<GitHubUserDTO> getDataFromGitHubApi(String login) {
        final String githubApiUrl = githubApi.replace("{login}", login);
        return webClientBuilder.build()
                .get()
                .uri(githubApiUrl)
                .retrieve()
                .bodyToFlux(GitHubUserDTO.class);
    }

    private void validateResponseFromExternalApi(Flux<GitHubUserDTO> userDTOFlux) {
        boolean responseFromExternalApiIsNull = isNull(userDTOFlux) || isNull(userDTOFlux.blockFirst());
        if (responseFromExternalApiIsNull) {
            throw new ExternalApiResponseException(EXTERNAL_API_CONNECTION_ERROR.getValue());
        }
    }

    private boolean followersAndPublicReposAreNotNull(Integer followers, Integer publicRepos) {
        return nonNull(followers) && nonNull(publicRepos);
    }

}
