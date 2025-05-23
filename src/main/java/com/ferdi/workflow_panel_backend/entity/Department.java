package com.ferdi.workflow_panel_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID departmentId;

    private String name;

    private String description;

    private String color;

    private Date createdDate;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<User> users;

    @Override
    public String toString() {
        return "Department{id=" + departmentId + ", name='" + name + "'}";
        // NOT: users yazma!
    }
}
