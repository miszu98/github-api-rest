package pl.malek.githubapirest.dto;

import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.time.LocalDateTime;

@Data
@Builder
public class GitHubUserWithoutDetailsDTO {

    private Long id;

    private String login;

    private String name;

    private String type;

    private URL avatarUrl;

    private LocalDateTime createdAt;

    private Double calculations;

    public static GitHubUserWithoutDetailsDTO map(GitHubUserDTO gitHubUserDTO) {
        return GitHubUserWithoutDetailsDTO.builder()
                .id(gitHubUserDTO.getId())
                .login(gitHubUserDTO.getLogin())
                .name(gitHubUserDTO.getName())
                .avatarUrl(gitHubUserDTO.getAvatarUrl())
                .createdAt(gitHubUserDTO.getCreatedAt())
                .type(gitHubUserDTO.getType())
                .calculations(gitHubUserDTO.getCalculations())
                .build();
    }

}
