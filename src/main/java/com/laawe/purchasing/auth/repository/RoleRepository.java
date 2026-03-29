package com.laawe.purchasing.auth.repository;

import com.laawe.purchasing.auth.model.entity.M_Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<M_Role, Long> {
    Optional<M_Role> findByName(String name);
    Optional<M_Role> findByIdf(UUID idf);
}
