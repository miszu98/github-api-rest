package pl.malek.githubapirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.malek.githubapirest.entity.GitHubUserCallsEntity;

import java.util.Optional;

@Repository
public interface GitHubUserCallRepository extends JpaRepository<GitHubUserCallsEntity, Long> {

    Optional<GitHubUserCallsEntity> findByUsername(String username);

}
