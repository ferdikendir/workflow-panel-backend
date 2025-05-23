package com.ferdi.workflow_panel_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID systemUserId;

    private String name;

    private String lastName;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    private Department department;

    public enum Role {
        ADMIN,
        EMPLOYEE
    }

    @Transient
    public String token;

    @Transient
    public String refreshToken;

    private Date createdDate;
}

