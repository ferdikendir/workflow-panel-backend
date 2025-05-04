package com.ferdi.workflow_panel_backend.service;


import com.ferdi.workflow_panel_backend.dto.DepartmantDto;
import com.ferdi.workflow_panel_backend.entity.Departmant;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface DepartmantService {
    ResponseEntity<ApiResponse<Departmant>> getDepartmantById(UUID id);
    ResponseEntity<ApiResponse<List<Departmant>>> getAllDepartmants();
    ResponseEntity<ApiResponse<Departmant>> createDeparmant(DepartmantDto departmant);
}
