package com.laawe.purchasing.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.laawe.purchasing.auth.config.constant.AppConstant.*;

@Entity
@Table(name = TABLE_USER_DETAIL, schema = PUBLIC)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class M_User_Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = USER_DETAIL_ID)
    private Long userDetailId;

    @Column(name = USER_DETAIL_IDF, unique = true, nullable = false, updatable = false)
    private UUID userDetailIdf = UUID.randomUUID();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = USER_ID, nullable = false)
    private M_User user;

    @Column(name = USER_AVATAR, columnDefinition = TEXT)
    private String userAvatar;

    @Column(name = USER_DETAIL_DEPARTMENT_NAME)
    private String userDetailDepartmentName;

    @Column(name = USER_OFFICE_LOCATION)
    private String userOfficeLocation;

    @Column(name = USER_DETAIL_CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime userDetailCreatedAt;

    @Column(name = USER_DETAIL_UPDATED_AT)
    private LocalDateTime userDetailUpdatedAt;

}