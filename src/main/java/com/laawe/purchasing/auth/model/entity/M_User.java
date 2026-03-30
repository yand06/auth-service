package com.laawe.purchasing.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.time.LocalDateTime;

import static com.laawe.purchasing.auth.config.constant.AppConstant.*;

@Entity
@Table(name = TABLE_USER, schema = PUBLIC)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class M_User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = USER_ID)
    private Long id;

    @Column(name = USER_IDF, unique = true, nullable = false)
    private UUID idf;

    @Column(name = USER_USERNAME, unique = true, nullable = false)
    private String username;

    @Column(name = USER_PHONE_NUMBER, unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = USER_EMAIL, unique = true, nullable = false)
    private String email;

    @Column(name = USER_PASSWORD_HASH, nullable = false)
    private String password;

    @Column(name = USER_FULL_NAME)
    private String fullName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = USER_ROLE_ID, nullable = false)
    private M_Role role;

    @Column(name = USER_IS_ACTIVE)
    private Boolean isActive = true;

    @Column(name = USER_CREATED_AT, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = USER_IS_ADMIN)
    private Boolean isAdmin = false;
}