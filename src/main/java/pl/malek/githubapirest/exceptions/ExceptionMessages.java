package pl.malek.githubapirest.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionMessages {

    EXTERNAL_API_CONNECTION_ERROR("Error connecting to API - the response is null");

    private String value;

    ExceptionMessages(String value) {
        this.value = value;
    }
}
