package com.ferdi.workflow_panel_backend.repository;

import com.ferdi.workflow_panel_backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentId(UUID departmentId);
    Optional<Department> findByName(String departmentName);

}

