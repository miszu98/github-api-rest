package pl.malek.githubapirest.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionMessages {

    EXTERNAL_API_CONNECTION_ERROR("Error connecting to API - the response is null"),
    LOGIN_CANNOT_BE_EMPTY_OR_BLANK("Login cannot be empty or blank text");

    private String value;

    ExceptionMessages(String value) {
        this.value = value;
    }
}
