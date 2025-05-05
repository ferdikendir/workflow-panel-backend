package com.ferdi.workflow_panel_backend.controller;

import com.ferdi.workflow_panel_backend.dto.DepartmentDto;
import com.ferdi.workflow_panel_backend.entity.Department;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import com.ferdi.workflow_panel_backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@CrossOrigin(origins = "*")
public class DepartmentController {

   @Autowired
   private DepartmentService departmentService;

   @PostMapping("/list")
    public ResponseEntity<ApiResponse<List<Department>>> listDepartment() {
       return departmentService.getAllDepartments();
   }

   @PostMapping("/create")
   public ResponseEntity<ApiResponse<Department>> createDepartment(@RequestBody DepartmentDto departmentDto) {
      return  departmentService.createDepartment(departmentDto);
   }
}
