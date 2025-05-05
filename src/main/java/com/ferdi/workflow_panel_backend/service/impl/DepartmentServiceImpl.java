package com.ferdi.workflow_panel_backend.service.impl;

import com.ferdi.workflow_panel_backend.constant.ResultMessage;
import com.ferdi.workflow_panel_backend.dto.DepartmentDto;
import com.ferdi.workflow_panel_backend.entity.Department;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import com.ferdi.workflow_panel_backend.payload.ResponseUtil;
import com.ferdi.workflow_panel_backend.repository.DepartmentRepository;
import com.ferdi.workflow_panel_backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public ResponseEntity<ApiResponse<Department>> getDepartmentById(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<List<Department>>> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return ResponseUtil.success(ResultMessage.none, departments);
    }

    @Override
    public ResponseEntity<ApiResponse<Department>> createDepartment(DepartmentDto department) {

        if (departmentRepository.findByName(department.getName()).isPresent()) {
            return ResponseUtil.successError(ResultMessage.sameDepartmentNameRegistered, null);
        }

        Department dep = new Department();
        dep.setName(department.getName());

        Department addedDepartment = departmentRepository.save(dep);
        return ResponseUtil.success(ResultMessage.createdSuccess, addedDepartment);
    }
}
