package com.laawe.purchasing.auth.repository;

import com.laawe.purchasing.auth.model.dto.UserProfileDTO;
import com.laawe.purchasing.auth.model.entity.M_User_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDetailRepository extends JpaRepository<M_User_Detail, Long> {

    @Query("""
        SELECT new com.laawe.purchasing.auth.model.dto.UserProfileDTO(
            ud.userDetailDepartmentName, ud.userAvatar, ud.userOfficeLocation,
            u.idf, u.fullName, u.email, u.employeeId, u.username, u.phoneNumber, u.status, u.isAdmin, u.createdAt,
            r.name)
        FROM M_User_Detail ud
        JOIN ud.user u
        JOIN u.role r
        WHERE u.idf = :userIdf
    """)
    Optional<UserProfileDTO> findProfileByUserIdf(@Param("userIdf") UUID userIdf);

}
