package com.ferdi.workflow_panel_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "department_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentUser {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID departmentUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentId", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "systemUserId", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false, updatable = false)
    private Date createdDate;

    private Date modifiedDate;

    @Enumerated(EnumType.STRING)
    private User.Role role;

    public enum Role {
        ADMIN,
        EMPLOYEE
    }
}