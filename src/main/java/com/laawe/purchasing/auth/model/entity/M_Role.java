package com.laawe.purchasing.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

import static com.laawe.purchasing.auth.config.constant.AppConstant.*;

@Entity
@Table(name = TABLE_ROLE, schema = PUBLIC)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class M_Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ROLE_ID)
    private Long id;

    @Column(name = ROLE_IDF, unique = true, nullable = false)
    private UUID idf;

    @Column(name = ROLE_NAME, unique = true, nullable = false)
    private String name;

    @Column(name = ROLE_DESCRIPTION)
    private String description;
}