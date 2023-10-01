package pl.malek.githubapirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.malek.githubapirest.entity.GitHubUserRequestEntity;

import java.util.Optional;

@Repository
public interface GitHubUserRequestRepository extends JpaRepository<GitHubUserRequestEntity, Long> {

    Optional<GitHubUserRequestEntity> findByLogin(String username);

}
