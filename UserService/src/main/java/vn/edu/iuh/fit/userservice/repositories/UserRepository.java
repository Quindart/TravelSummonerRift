package vn.edu.iuh.fit.userservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.iuh.fit.userservice.entities.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<User> findByEmail(String username);
    Optional<List<User>> findByIsActive(Boolean isActive);
}
