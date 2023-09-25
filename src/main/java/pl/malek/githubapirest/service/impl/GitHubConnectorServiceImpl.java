package pl.malek.githubapirest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.malek.githubapirest.dto.UserDTO;
import pl.malek.githubapirest.exceptions.ExternalApiResponseException;
import pl.malek.githubapirest.service.ApiExternalConnectorService;
import reactor.core.publisher.Flux;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.malek.githubapirest.exceptions.ExceptionMessages.EXTERNAL_API_CONNECTION_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubConnectorServiceImpl implements ApiExternalConnectorService {

    @Value("${github.api}")
    private String githubApi;

    private final WebClient.Builder webClientBuilder;

    @Override
    public UserDTO getUserDetailsByUsername(String username) {
        Flux<UserDTO> userDTOFlux = getDataFromGitHubApi(username);
        validateResponseFromExternalApi(userDTOFlux);

        UserDTO userDTO = userDTOFlux.blockFirst();
        doCalculations(userDTO);

        return userDTO;
    }

    private void doCalculations(UserDTO userDTO) {
        Integer followers = userDTO.getFollowers();
        Integer publicRepos = userDTO.getPublicRepos();

        if (followersAndPublicReposAreNotNull(followers, publicRepos)) {
            Double calculateResult = calculate(followers, publicRepos);
            userDTO.setCalculations(calculateResult);
        }
    }

    private Double calculate(Integer followers, Integer publicRepos) {
        boolean followersIsNotZero = followers != 0;
        if (followersIsNotZero) {
            return ((double) 6 / followers) * (2 + publicRepos);
        } return null;
    }

    private Flux<UserDTO> getDataFromGitHubApi(String username) {
        final String githubApiUrl = githubApi.replace("{username}", username);
        return webClientBuilder.build()
                .get()
                .uri(githubApiUrl)
                .retrieve()
                .bodyToFlux(UserDTO.class);
    }

    private void validateResponseFromExternalApi(Flux<UserDTO> userDTOFlux) {
        if (isNull(userDTOFlux)) {
            throw new ExternalApiResponseException(EXTERNAL_API_CONNECTION_ERROR.getValue());
        }
    }

    private boolean followersAndPublicReposAreNotNull(Integer followers, Integer publicRepos) {
        return nonNull(followers) && nonNull(publicRepos);
    }

}
