package pl.malek.githubapirest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.malek.githubapirest.dto.GitHubUserDTO;
import pl.malek.githubapirest.service.GitHubUserCallFacade;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class GitHubUserController {

    private final GitHubUserCallFacade gitHubUserCallFacade;

    @GetMapping("/{username}")
    public ResponseEntity<GitHubUserDTO> getUserDetailsByUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(gitHubUserCallFacade.getAndUpdateUserCall(username));
    }

}
