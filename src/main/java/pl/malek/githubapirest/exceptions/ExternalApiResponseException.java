package pl.malek.githubapirest.exceptions;

public class ExternalApiResponseException extends RuntimeException{
    public ExternalApiResponseException(String message) {
        super(message);
    }
}
