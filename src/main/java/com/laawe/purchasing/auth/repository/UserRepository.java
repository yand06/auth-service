package com.laawe.purchasing.auth.repository;

import com.laawe.purchasing.auth.model.entity.M_User;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
