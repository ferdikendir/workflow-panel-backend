package com.ferdi.workflow_panel_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "departmants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Departmant {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID departmantId;

    private String name;
}
