package pl.malek.githubapirest.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionMessages {

    EXTERNAL_API_CONNECTION_ERROR("Error connecting to API - the response is null"),
    USERNAME_CANNOT_BE_EMPTY_OR_BLANK("Username cannot be empty or blank text");

    private String value;

    ExceptionMessages(String value) {
        this.value = value;
    }
}
