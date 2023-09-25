package pl.malek.githubapirest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/{username}")
    public ResponseEntity<Void> getUserDetailsByUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
