package com.laawe.purchasing.auth.repository;

import com.laawe.purchasing.auth.model.entity.M_User;
import com.laawe.purchasing.auth.model.entity.M_User_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<M_User_Detail, Long> {
    Optional<M_User_Detail> findByUser(M_User user);
}
