package pl.malek.githubapirest.utils;

import pl.malek.githubapirest.dto.GitHubUserDTO;
import pl.malek.githubapirest.entity.GitHubUserCallsEntity;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

public class MockDataUtils {

    public static GitHubUserCallsEntity getFakeGitHubUserCallsEntity() {
        return GitHubUserCallsEntity.builder()
                .callsNumber(10L)
                .username(getFakeUsername())
                .version(4)
                .id(200L)
                .build();
    }

    public static String getFakePatternExternalApiUrl() {
        return "https://fake.external-api.com/users/{username}";
    }

    public static String getFakeExternalApiUrl() {
        return getFakePatternExternalApiUrl().replace("{username}", getFakeUsername());
    }

    public static String getFakeUsername() {
        return "mock_test_username";
    }

    public static String getFakeNotExistingUsername() {
        return "new_user";
    }

    public static String getAccountType() {
        return "user";
    }

    public static String getFakeName() {
        return "Robert C. Martin";
    }

    public static GitHubUserDTO getFakeUserDTO() throws MalformedURLException {
        return GitHubUserDTO.builder()
                .id(1001L)
                .login(getFakeUsername())
                .avatarUrl(new URL(getFakeExternalApiUrl()))
                .createdAt(LocalDateTime.now())
                .type(getAccountType())
                .name(getFakeName())
                .build();
    }

    public static GitHubUserDTO getFakeUserWithFollowersZero() throws MalformedURLException {
        GitHubUserDTO gitHubUserDTO = getFakeUserDTO();
        gitHubUserDTO.setFollowers(0);
        return gitHubUserDTO;
    }

    public static GitHubUserDTO getFakeUserWithFollowersNotZero() throws MalformedURLException {
        GitHubUserDTO gitHubUserDTO = getFakeUserDTO();
        gitHubUserDTO.setFollowers(55);
        gitHubUserDTO.setPublicRepos(5);
        return gitHubUserDTO;
    }

}
