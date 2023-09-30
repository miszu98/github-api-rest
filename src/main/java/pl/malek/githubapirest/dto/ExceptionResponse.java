package pl.malek.githubapirest.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionResponse {

    private String errorMessage;

    private LocalDateTime errorTime;

    private Integer errorStatus;

}
