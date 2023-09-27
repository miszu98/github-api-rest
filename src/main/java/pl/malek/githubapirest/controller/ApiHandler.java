package pl.malek.githubapirest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.malek.githubapirest.exceptions.ExternalApiResponseException;

import java.net.UnknownHostException;

@Slf4j
@RestControllerAdvice
public class ApiHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException exception) {
        log.debug("Exception thrown: {0}", exception);
        return ResponseEntity.status(HttpStatus.OK).body(exception.getMessage());
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<String> handleUnknownHostException(UnknownHostException exception) {
        log.debug("Exception thrown: {0}", exception);
        return ResponseEntity.status(HttpStatus.OK).body(exception.getMessage());
    }

    @ExceptionHandler(ExternalApiResponseException.class)
    public ResponseEntity<String> handleExternalApiResponseException(ExternalApiResponseException exception) {
        log.debug("Exception thrown: {0}", exception);
        return ResponseEntity.status(HttpStatus.OK).body(exception.getMessage());
    }

}
