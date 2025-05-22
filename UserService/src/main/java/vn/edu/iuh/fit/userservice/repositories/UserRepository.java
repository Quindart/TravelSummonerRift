package vn.edu.iuh.fit.userservice.repositories;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.userservice.entities.User;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<User> findByEmail(String username);
    Optional<List<User>> findByIsActive(Boolean isActive);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.recipientToken = :token WHERE u.userId = :userId")
    void updateRecipientTokenByUserId(@Param("userId") String userId, @Param("token") String token);

}
