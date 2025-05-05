package com.ferdi.workflow_panel_backend.service;


import com.ferdi.workflow_panel_backend.dto.DepartmentDto;
import com.ferdi.workflow_panel_backend.entity.Department;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {
    ResponseEntity<ApiResponse<Department>> getDepartmentById(UUID id);
    ResponseEntity<ApiResponse<List<Department>>> getAllDepartments();
    ResponseEntity<ApiResponse<Department>> createDepartment(DepartmentDto department);
}
