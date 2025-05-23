package com.ferdi.workflow_panel_backend.service;


import com.ferdi.workflow_panel_backend.dto.DepartmentDto;
import com.ferdi.workflow_panel_backend.entity.Department;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {
    ResponseEntity<ApiResponse<Department>> getDepartmentById(UUID id);
    ResponseEntity<ApiResponse<List<DepartmentDto>>> getAllDepartments(DepartmentDto departmentDto);
    ResponseEntity<ApiResponse<DepartmentDto>> createDepartment(DepartmentDto department);
    ResponseEntity<ApiResponse<DepartmentDto>> updateDepartment(DepartmentDto department);
    ResponseEntity<ApiResponse<DepartmentDto>> deleteDepartment(UUID id);
    ResponseEntity<ApiResponse<DepartmentDto>> getMembers(DepartmentDto departmentDto);
}
