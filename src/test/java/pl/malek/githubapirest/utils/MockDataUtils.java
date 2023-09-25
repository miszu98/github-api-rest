package pl.malek.githubapirest.utils;

import pl.malek.githubapirest.dto.UserDTO;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

public class MockDataUtils {

    public static String getFakePatternExternalApiUrl() {
        return "https://fake.external-api.com/users/{username}";
    }

    public static String getFakeExternalApiUrl() {
        return getFakePatternExternalApiUrl().replace("{username}", getFakeUsername());
    }

    public static String getFakeUsername() {
        return "mock_test_username";
    }

    public static String getAccountType() {
        return "user";
    }

    public static String getFakeName() {
        return "Robert C. Martin";
    }

    public static UserDTO getFakeUserDTO() throws MalformedURLException {
        return UserDTO.builder()
                .id(1001L)
                .login(getFakeUsername())
                .avatarUrl(new URL(getFakePatternExternalApiUrl()))
                .createdAt(LocalDateTime.now())
                .type(getAccountType())
                .name(getFakeName())
                .build();
    }

    public static UserDTO getFakeUserWithFollowersZero() throws MalformedURLException {
        UserDTO userDTO = getFakeUserDTO();
        userDTO.setFollowers(0);
        return userDTO;
    }

    public static UserDTO getFakeUserWithFollowersNotZero() throws MalformedURLException {
        UserDTO userDTO = getFakeUserDTO();
        userDTO.setFollowers(55);
        userDTO.setPublicRepos(5);
        return userDTO;
    }

}
