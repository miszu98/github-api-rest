package pl.malek.githubapirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubUserDTO extends GitHubUserStatsDTO {

    private Long id;

    private String login;

    private String name;

    private String type;

    @JsonProperty("avatar_url")
    private URL avatarUrl;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    private Double calculations;

}
