package az.company.userservice.dao.repository;

import az.company.userservice.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT COUNT(u) FROM UserEntity u")
    Long countByUsers();
}
