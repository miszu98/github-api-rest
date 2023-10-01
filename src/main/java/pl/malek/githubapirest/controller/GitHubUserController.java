package pl.malek.githubapirest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.malek.githubapirest.dto.GitHubUserWithoutDetailsDTO;
import pl.malek.githubapirest.service.GitHubUserRequestFacade;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class GitHubUserController {

    private final GitHubUserRequestFacade gitHubUserRequestFacade;

    @GetMapping("/{login}")
    public ResponseEntity<GitHubUserWithoutDetailsDTO> getUserDetailsByUsername(@PathVariable String login) {
        return ResponseEntity.status(HttpStatus.OK).body(gitHubUserRequestFacade.getAndUpdateUserRequest(login));
    }

}
