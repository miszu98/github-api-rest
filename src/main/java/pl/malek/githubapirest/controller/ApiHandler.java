package pl.malek.githubapirest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.malek.githubapirest.dto.ExceptionResponse;
import pl.malek.githubapirest.exceptions.ExternalApiResponseException;
import pl.malek.githubapirest.exceptions.ValidationException;

import java.net.UnknownHostException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ApiHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ExceptionResponse> handleWebClientResponseException(WebClientResponseException exception) {
        log.debug("Exception thrown: ", exception);
        return ResponseEntity.status(HttpStatus.OK).body(buildExceptionResponse(exception.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<ExceptionResponse> handleUnknownHostException(UnknownHostException exception) {
        log.debug("Exception thrown: ", exception);
        return ResponseEntity.status(HttpStatus.OK).body(buildExceptionResponse(exception.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(ExternalApiResponseException.class)
    public ResponseEntity<ExceptionResponse> handleExternalApiResponseException(ExternalApiResponseException exception) {
        log.debug("Exception thrown: ", exception);
        return ResponseEntity.status(HttpStatus.OK).body(buildExceptionResponse(exception.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(ValidationException exception) {
        log.debug("Exception thrown: ", exception);
        return ResponseEntity.status(HttpStatus.OK).body(buildExceptionResponse(exception.getMessage(), HttpStatus.CONFLICT));
    }

    private ExceptionResponse buildExceptionResponse(String errorMessage, HttpStatus httpStatus) {
        return ExceptionResponse.builder()
                .errorMessage(errorMessage)
                .errorStatus(httpStatus.value())
                .errorTime(LocalDateTime.now())
                .build();
    }

}
