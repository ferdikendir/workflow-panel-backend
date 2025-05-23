package com.ferdi.workflow_panel_backend.controller;

import com.ferdi.workflow_panel_backend.dto.DepartmentDto;
import com.ferdi.workflow_panel_backend.entity.Department;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import com.ferdi.workflow_panel_backend.service.DepartmentService;
import com.ferdi.workflow_panel_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/department")
@CrossOrigin(origins = "*")
public class DepartmentController {

   @Autowired
   private DepartmentService departmentService;
    @Autowired
    private UserService userService;

   @PostMapping("/list")
    public ResponseEntity<ApiResponse<List<DepartmentDto>>> listDepartment(@RequestBody DepartmentDto departmentDto) {
       return departmentService.getAllDepartments(departmentDto);
   }

   @PostMapping("/create")
   public ResponseEntity<ApiResponse<DepartmentDto>> createDepartment(@RequestBody DepartmentDto departmentDto) {
      return  departmentService.createDepartment(departmentDto);
   }

   @PostMapping("/delete")
   public ResponseEntity<ApiResponse<DepartmentDto>> deleteDepartment(@RequestBody DepartmentDto departmentDto) {
      return departmentService.deleteDepartment(departmentDto.getDepartmentId());
   }
   @PostMapping("/getMembers")
   public ResponseEntity<ApiResponse<DepartmentDto>> getMembers(@RequestBody DepartmentDto departmentDto) {
      return departmentService.getMembers(departmentDto);
   }
}
