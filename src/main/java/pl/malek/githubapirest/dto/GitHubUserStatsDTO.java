package pl.malek.githubapirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitHubUserStatsDTO {
    private Integer followers;

    @JsonProperty("public_repos")
    private Integer publicRepos;
}
