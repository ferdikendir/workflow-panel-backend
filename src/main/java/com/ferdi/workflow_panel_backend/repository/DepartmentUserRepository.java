package com.ferdi.workflow_panel_backend.repository;

import com.ferdi.workflow_panel_backend.entity.Department;
import com.ferdi.workflow_panel_backend.entity.DepartmentUser;
import com.ferdi.workflow_panel_backend.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DepartmentUserRepository extends JpaRepository<DepartmentUser, Long> {
    List<DepartmentUser> findByUser(User user, Sort sort);
    List<DepartmentUser> findByDepartment(Department department);
    DepartmentUser findByDepartmentAndUserAndActive(Department department, User user, boolean active);

}
