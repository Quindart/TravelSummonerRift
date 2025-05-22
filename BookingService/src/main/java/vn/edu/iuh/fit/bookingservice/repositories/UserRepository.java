package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository()
public interface UserRepository extends org.springframework.data.repository.Repository<Object, Long> {
    @Query(value = "SELECT recipient_token FROM users WHERE user_id = :userId", nativeQuery = true)
    String findRecipientTokenByUserId(@Param("userId") String userId);
}
