package com.laawe.purchasing.auth.repository;

import com.laawe.purchasing.auth.model.entity.M_User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<M_User, Long> {
    Optional<M_User> findByUsername(String username);
    Optional<M_User> findByEmail(String email);
    Optional<M_User> findByIdf(UUID idf);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM M_User u WHERE u.username = :identifier OR u.email = :identifier OR u.phoneNumber = :identifier")
    Optional<M_User> findByIdentifier(@Param("identifier") String identifier);
}
