package com.ferdi.workflow_panel_backend.repository;

import com.ferdi.workflow_panel_backend.entity.Departmant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DepartmantRepository extends JpaRepository<Departmant, Long> {

    Optional<Departmant> findByDepartmantId(UUID departmantId);

}

