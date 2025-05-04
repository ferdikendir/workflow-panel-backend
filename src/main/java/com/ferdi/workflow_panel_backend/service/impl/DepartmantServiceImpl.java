package com.ferdi.workflow_panel_backend.service.impl;

import com.ferdi.workflow_panel_backend.dto.DepartmantDto;
import com.ferdi.workflow_panel_backend.entity.Departmant;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import com.ferdi.workflow_panel_backend.payload.ResponseUtil;
import com.ferdi.workflow_panel_backend.repository.DepartmantRepository;
import com.ferdi.workflow_panel_backend.service.DepartmantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmantServiceImpl implements DepartmantService {

    @Autowired
    private DepartmantRepository departmantRepository;

    @Override
    public ResponseEntity<ApiResponse<Departmant>> getDepartmantById(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<List<Departmant>>> getAllDepartmants() {
        List<Departmant> departmants = departmantRepository.findAll();
        return ResponseUtil.success("", departmants);
    }

    @Override
    public ResponseEntity<ApiResponse<Departmant>> createDeparmant(DepartmantDto departmant) {
        Departmant dep = new Departmant();
        dep.setName(departmant.getName());

        Departmant addedDeparmant = departmantRepository.save(dep);
        return ResponseUtil.success("Created", addedDeparmant);
    }
}
